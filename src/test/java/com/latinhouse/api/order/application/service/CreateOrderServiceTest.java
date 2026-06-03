package com.latinhouse.api.order.application.service;

import com.latinhouse.api.common.exception.LessonOptionNotFoundException;
import com.latinhouse.api.common.exception.ProfileNotFoundException;
import com.latinhouse.api.lesson.application.port.out.LoadLessonPort;
import com.latinhouse.api.lesson.domain.DiscountType;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.domain.LessonDiscount;
import com.latinhouse.api.order.application.port.in.CreateOrderAppRequest;
import com.latinhouse.api.order.application.port.in.CreateOrderAppResponse;
import com.latinhouse.api.order.application.port.out.LoadLessonOptionPort;
import com.latinhouse.api.order.application.port.out.SaveOrderPort;
import com.latinhouse.api.order.domain.Order;
import com.latinhouse.api.order.domain.OrderDiscountType;
import com.latinhouse.api.order.domain.OrderStatus;
import com.latinhouse.api.profile.application.port.out.FindProfilePort;
import com.latinhouse.api.profile.domain.Profile;
import com.latinhouse.api.profile.domain.Sex;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateOrderServiceTest {

    @InjectMocks
    private CreateOrderService createOrderService;

    @Mock
    private LoadLessonPort loadLessonPort;

    @Mock
    private LoadLessonOptionPort loadLessonOptionPort;

    @Mock
    private FindProfilePort findProfilePort;

    @Mock
    private SaveOrderPort saveOrderPort;

    private CreateOrderAppRequest validRequest() {
        return CreateOrderAppRequest.builder()
                .lessonNo(1L)
                .lessonOptionNo(3L)
                .profileId("Ab2Cd3Ef")
                .build();
    }

    private Order savedOrderWith(List<com.latinhouse.api.order.domain.OrderDiscount> discounts) {
        return Order.builder()
                .id("uuid-123")
                .lessonNo(1L)
                .lessonOptionNo(3L)
                .buyer("Ab2Cd3Ef")
                .price(BigDecimal.valueOf(80000))
                .discounts(discounts)
                .status(OrderStatus.PAYMENT_PENDING)
                .build();
    }

    // ── 기존 테스트 ──────────────────────────────────────────────────────────

    @Test
    void createOrder_validRequest_returnsOrderId() {
        Lesson lesson = Lesson.builder().id(1L).amount(BigDecimal.valueOf(80000)).discounts(List.of()).build();
        when(loadLessonPort.loadLesson(1L)).thenReturn(lesson);
        doNothing().when(loadLessonOptionPort).load(3L);
        Profile profile = Profile.builder().id("Ab2Cd3Ef").sex(Sex.M).build();
        when(findProfilePort.findById("Ab2Cd3Ef")).thenReturn(Optional.of(profile));
        when(saveOrderPort.save(any())).thenReturn(savedOrderWith(List.of()));

        CreateOrderAppResponse response = createOrderService.createOrder(validRequest());

        assertThat(response.getOrderId()).isEqualTo("uuid-123");
        verify(loadLessonPort).loadLesson(1L);
        verify(loadLessonOptionPort).load(3L);
        verify(findProfilePort).findById("Ab2Cd3Ef");
        verify(saveOrderPort).save(any());
    }

    @Test
    void createOrder_lessonNotFound_throwsLessonNotFoundException() {
        when(loadLessonPort.loadLesson(anyLong()))
                .thenThrow(new com.latinhouse.api.common.exception.LessonNotFoundException(1L));

        assertThatThrownBy(() -> createOrderService.createOrder(validRequest()))
                .isInstanceOf(com.latinhouse.api.common.exception.LessonNotFoundException.class);
    }

    @Test
    void createOrder_lessonOptionNotFound_throwsLessonOptionNotFoundException() {
        Lesson lesson = Lesson.builder().id(1L).amount(BigDecimal.valueOf(80000)).discounts(List.of()).build();
        when(loadLessonPort.loadLesson(1L)).thenReturn(lesson);
        doThrow(new LessonOptionNotFoundException(3L))
                .when(loadLessonOptionPort).load(anyLong());

        assertThatThrownBy(() -> createOrderService.createOrder(validRequest()))
                .isInstanceOf(LessonOptionNotFoundException.class);
    }

    @Test
    void createOrder_profileNotFound_throwsProfileNotFoundException() {
        Lesson lesson = Lesson.builder().id(1L).amount(BigDecimal.valueOf(80000)).discounts(List.of()).build();
        when(loadLessonPort.loadLesson(1L)).thenReturn(lesson);
        doNothing().when(loadLessonOptionPort).load(anyLong());
        when(findProfilePort.findById(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createOrderService.createOrder(validRequest()))
                .isInstanceOf(ProfileNotFoundException.class);
    }

    // ── US1: SEX 할인 ────────────────────────────────────────────────────────

    @Test
    void createOrder_sexDiscount_matching_M_included() {
        LessonDiscount sexDiscount = LessonDiscount.builder()
                .id(10L).type(DiscountType.SEX).condition("M").amount(BigDecimal.valueOf(5000)).build();
        Lesson lesson = Lesson.builder().id(1L).amount(BigDecimal.valueOf(80000))
                .discounts(List.of(sexDiscount)).build();
        when(loadLessonPort.loadLesson(1L)).thenReturn(lesson);
        doNothing().when(loadLessonOptionPort).load(3L);
        Profile profile = Profile.builder().id("Ab2Cd3Ef").sex(Sex.M).build();
        when(findProfilePort.findById("Ab2Cd3Ef")).thenReturn(Optional.of(profile));
        when(saveOrderPort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        createOrderService.createOrder(validRequest());

        org.mockito.ArgumentCaptor<Order> captor = org.mockito.ArgumentCaptor.forClass(Order.class);
        verify(saveOrderPort).save(captor.capture());
        Order saved = captor.getValue();
        assertThat(saved.getDiscounts()).hasSize(1);
        assertThat(saved.getDiscounts().get(0).getDiscountType()).isEqualTo(OrderDiscountType.LESSON);
        assertThat(saved.getDiscounts().get(0).getDiscountId()).isEqualTo(10L);
        assertThat(saved.getDiscounts().get(0).getAmount()).isEqualByComparingTo(BigDecimal.valueOf(5000));
    }

    @Test
    void createOrder_sexDiscount_notMatching_excluded() {
        LessonDiscount sexDiscount = LessonDiscount.builder()
                .id(10L).type(DiscountType.SEX).condition("M").amount(BigDecimal.valueOf(5000)).build();
        Lesson lesson = Lesson.builder().id(1L).amount(BigDecimal.valueOf(80000))
                .discounts(List.of(sexDiscount)).build();
        when(loadLessonPort.loadLesson(1L)).thenReturn(lesson);
        doNothing().when(loadLessonOptionPort).load(3L);
        Profile profile = Profile.builder().id("Ab2Cd3Ef").sex(Sex.F).build();
        when(findProfilePort.findById("Ab2Cd3Ef")).thenReturn(Optional.of(profile));
        when(saveOrderPort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        createOrderService.createOrder(validRequest());

        org.mockito.ArgumentCaptor<Order> captor = org.mockito.ArgumentCaptor.forClass(Order.class);
        verify(saveOrderPort).save(captor.capture());
        assertThat(captor.getValue().getDiscounts()).isEmpty();
    }

    @Test
    void createOrder_noDiscounts_emptyList() {
        Lesson lesson = Lesson.builder().id(1L).amount(BigDecimal.valueOf(80000))
                .discounts(List.of()).build();
        when(loadLessonPort.loadLesson(1L)).thenReturn(lesson);
        doNothing().when(loadLessonOptionPort).load(3L);
        Profile profile = Profile.builder().id("Ab2Cd3Ef").sex(Sex.M).build();
        when(findProfilePort.findById("Ab2Cd3Ef")).thenReturn(Optional.of(profile));
        when(saveOrderPort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        createOrderService.createOrder(validRequest());

        org.mockito.ArgumentCaptor<Order> captor = org.mockito.ArgumentCaptor.forClass(Order.class);
        verify(saveOrderPort).save(captor.capture());
        assertThat(captor.getValue().getDiscounts()).isEmpty();
    }

    // ── US2: EARLYBIRD 할인 ──────────────────────────────────────────────────

    @Test
    void createOrder_earlybirdDiscount_single_included() {
        String futureDate = LocalDate.now().plusDays(30).toString();
        LessonDiscount earlybird = LessonDiscount.builder()
                .id(20L).type(DiscountType.EARLYBIRD).condition(futureDate).amount(BigDecimal.valueOf(3000)).build();
        Lesson lesson = Lesson.builder().id(1L).amount(BigDecimal.valueOf(80000))
                .discounts(List.of(earlybird)).build();
        when(loadLessonPort.loadLesson(1L)).thenReturn(lesson);
        doNothing().when(loadLessonOptionPort).load(3L);
        Profile profile = Profile.builder().id("Ab2Cd3Ef").sex(Sex.M).build();
        when(findProfilePort.findById("Ab2Cd3Ef")).thenReturn(Optional.of(profile));
        when(saveOrderPort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        createOrderService.createOrder(validRequest());

        org.mockito.ArgumentCaptor<Order> captor = org.mockito.ArgumentCaptor.forClass(Order.class);
        verify(saveOrderPort).save(captor.capture());
        assertThat(captor.getValue().getDiscounts()).hasSize(1);
        assertThat(captor.getValue().getDiscounts().get(0).getDiscountId()).isEqualTo(20L);
    }

    @Test
    void createOrder_earlybirdDiscount_multiple_earliestSelected() {
        String future60 = LocalDate.now().plusDays(60).toString();
        String future30 = LocalDate.now().plusDays(30).toString();
        LessonDiscount earlybird1 = LessonDiscount.builder()
                .id(21L).type(DiscountType.EARLYBIRD).condition(future60).amount(BigDecimal.valueOf(2000)).build();
        LessonDiscount earlybird2 = LessonDiscount.builder()
                .id(22L).type(DiscountType.EARLYBIRD).condition(future30).amount(BigDecimal.valueOf(3000)).build();
        Lesson lesson = Lesson.builder().id(1L).amount(BigDecimal.valueOf(80000))
                .discounts(List.of(earlybird1, earlybird2)).build();
        when(loadLessonPort.loadLesson(1L)).thenReturn(lesson);
        doNothing().when(loadLessonOptionPort).load(3L);
        Profile profile = Profile.builder().id("Ab2Cd3Ef").sex(Sex.M).build();
        when(findProfilePort.findById("Ab2Cd3Ef")).thenReturn(Optional.of(profile));
        when(saveOrderPort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        createOrderService.createOrder(validRequest());

        org.mockito.ArgumentCaptor<Order> captor = org.mockito.ArgumentCaptor.forClass(Order.class);
        verify(saveOrderPort).save(captor.capture());
        assertThat(captor.getValue().getDiscounts()).hasSize(1);
        // future30 < future60 이므로 id=22 항목만 선택
        assertThat(captor.getValue().getDiscounts().get(0).getDiscountId()).isEqualTo(22L);
    }

    // ── US3: SEX + EARLYBIRD 복합 할인 ──────────────────────────────────────

    @Test
    void createOrder_mixedDiscounts_sexMatch_bothApplied() {
        LessonDiscount sexDiscount = LessonDiscount.builder()
                .id(10L).type(DiscountType.SEX).condition("M").amount(BigDecimal.valueOf(5000)).build();
        LessonDiscount earlybird = LessonDiscount.builder()
                .id(20L).type(DiscountType.EARLYBIRD).condition(LocalDate.now().plusDays(30).toString()).amount(BigDecimal.valueOf(3000)).build();
        Lesson lesson = Lesson.builder().id(1L).amount(BigDecimal.valueOf(80000))
                .discounts(List.of(sexDiscount, earlybird)).build();
        when(loadLessonPort.loadLesson(1L)).thenReturn(lesson);
        doNothing().when(loadLessonOptionPort).load(3L);
        Profile profile = Profile.builder().id("Ab2Cd3Ef").sex(Sex.M).build();
        when(findProfilePort.findById("Ab2Cd3Ef")).thenReturn(Optional.of(profile));
        when(saveOrderPort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        createOrderService.createOrder(validRequest());

        org.mockito.ArgumentCaptor<Order> captor = org.mockito.ArgumentCaptor.forClass(Order.class);
        verify(saveOrderPort).save(captor.capture());
        assertThat(captor.getValue().getDiscounts()).hasSize(2);
    }

    // ── US1: EARLYBIRD 만료일 필터 ───────────────────────────────────────────

    @Test
    void createOrder_earlybirdDiscount_expired_excluded() {
        String pastDate = LocalDate.now().minusDays(1).toString();
        LessonDiscount earlybird = LessonDiscount.builder()
                .id(20L).type(DiscountType.EARLYBIRD).condition(pastDate).amount(BigDecimal.valueOf(3000)).build();
        Lesson lesson = Lesson.builder().id(1L).amount(BigDecimal.valueOf(80000))
                .discounts(List.of(earlybird)).build();
        when(loadLessonPort.loadLesson(1L)).thenReturn(lesson);
        doNothing().when(loadLessonOptionPort).load(3L);
        Profile profile = Profile.builder().id("Ab2Cd3Ef").sex(Sex.M).build();
        when(findProfilePort.findById("Ab2Cd3Ef")).thenReturn(Optional.of(profile));
        when(saveOrderPort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        createOrderService.createOrder(validRequest());

        org.mockito.ArgumentCaptor<Order> captor = org.mockito.ArgumentCaptor.forClass(Order.class);
        verify(saveOrderPort).save(captor.capture());
        assertThat(captor.getValue().getDiscounts()).isEmpty();
    }

    @Test
    void createOrder_earlybirdDiscount_today_included() {
        String todayDate = LocalDate.now().toString();
        LessonDiscount earlybird = LessonDiscount.builder()
                .id(20L).type(DiscountType.EARLYBIRD).condition(todayDate).amount(BigDecimal.valueOf(3000)).build();
        Lesson lesson = Lesson.builder().id(1L).amount(BigDecimal.valueOf(80000))
                .discounts(List.of(earlybird)).build();
        when(loadLessonPort.loadLesson(1L)).thenReturn(lesson);
        doNothing().when(loadLessonOptionPort).load(3L);
        Profile profile = Profile.builder().id("Ab2Cd3Ef").sex(Sex.M).build();
        when(findProfilePort.findById("Ab2Cd3Ef")).thenReturn(Optional.of(profile));
        when(saveOrderPort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        createOrderService.createOrder(validRequest());

        org.mockito.ArgumentCaptor<Order> captor = org.mockito.ArgumentCaptor.forClass(Order.class);
        verify(saveOrderPort).save(captor.capture());
        assertThat(captor.getValue().getDiscounts()).hasSize(1);
        assertThat(captor.getValue().getDiscounts().get(0).getDiscountId()).isEqualTo(20L);
    }

    @Test
    void createOrder_earlybirdDiscount_mixedExpiry_validOnly() {
        String pastDate = LocalDate.now().minusDays(1).toString();
        String futureDate = LocalDate.now().plusDays(30).toString();
        LessonDiscount expired = LessonDiscount.builder()
                .id(21L).type(DiscountType.EARLYBIRD).condition(pastDate).amount(BigDecimal.valueOf(2000)).build();
        LessonDiscount valid = LessonDiscount.builder()
                .id(22L).type(DiscountType.EARLYBIRD).condition(futureDate).amount(BigDecimal.valueOf(3000)).build();
        Lesson lesson = Lesson.builder().id(1L).amount(BigDecimal.valueOf(80000))
                .discounts(List.of(expired, valid)).build();
        when(loadLessonPort.loadLesson(1L)).thenReturn(lesson);
        doNothing().when(loadLessonOptionPort).load(3L);
        Profile profile = Profile.builder().id("Ab2Cd3Ef").sex(Sex.M).build();
        when(findProfilePort.findById("Ab2Cd3Ef")).thenReturn(Optional.of(profile));
        when(saveOrderPort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        createOrderService.createOrder(validRequest());

        org.mockito.ArgumentCaptor<Order> captor = org.mockito.ArgumentCaptor.forClass(Order.class);
        verify(saveOrderPort).save(captor.capture());
        assertThat(captor.getValue().getDiscounts()).hasSize(1);
        assertThat(captor.getValue().getDiscounts().get(0).getDiscountId()).isEqualTo(22L);
    }

    @Test
    void createOrder_mixedDiscounts_sexNotMatch_onlyEarlybird() {
        LessonDiscount sexDiscount = LessonDiscount.builder()
                .id(10L).type(DiscountType.SEX).condition("M").amount(BigDecimal.valueOf(5000)).build();
        LessonDiscount earlybird = LessonDiscount.builder()
                .id(20L).type(DiscountType.EARLYBIRD).condition(LocalDate.now().plusDays(30).toString()).amount(BigDecimal.valueOf(3000)).build();
        Lesson lesson = Lesson.builder().id(1L).amount(BigDecimal.valueOf(80000))
                .discounts(List.of(sexDiscount, earlybird)).build();
        when(loadLessonPort.loadLesson(1L)).thenReturn(lesson);
        doNothing().when(loadLessonOptionPort).load(3L);
        Profile profile = Profile.builder().id("Ab2Cd3Ef").sex(Sex.F).build();
        when(findProfilePort.findById("Ab2Cd3Ef")).thenReturn(Optional.of(profile));
        when(saveOrderPort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        createOrderService.createOrder(validRequest());

        org.mockito.ArgumentCaptor<Order> captor = org.mockito.ArgumentCaptor.forClass(Order.class);
        verify(saveOrderPort).save(captor.capture());
        assertThat(captor.getValue().getDiscounts()).hasSize(1);
        assertThat(captor.getValue().getDiscounts().get(0).getDiscountId()).isEqualTo(20L);
    }
}
