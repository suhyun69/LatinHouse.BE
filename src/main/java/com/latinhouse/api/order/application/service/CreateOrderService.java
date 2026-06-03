package com.latinhouse.api.order.application.service;

import com.latinhouse.api.common.exception.ProfileNotFoundException;
import com.latinhouse.api.lesson.application.port.out.LoadLessonPort;
import com.latinhouse.api.lesson.domain.DiscountType;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.domain.LessonDiscount;
import com.latinhouse.api.order.application.port.in.CreateOrderAppRequest;
import com.latinhouse.api.order.application.port.in.CreateOrderAppResponse;
import com.latinhouse.api.order.application.port.in.CreateOrderUseCase;
import com.latinhouse.api.order.application.port.out.LoadLessonOptionPort;
import com.latinhouse.api.order.application.port.out.SaveOrderPort;
import com.latinhouse.api.order.domain.Order;
import com.latinhouse.api.order.domain.OrderDiscount;
import com.latinhouse.api.order.domain.OrderDiscountType;
import com.latinhouse.api.order.domain.OrderStatus;
import com.latinhouse.api.profile.application.port.out.FindProfilePort;
import com.latinhouse.api.profile.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateOrderService implements CreateOrderUseCase {

    private final LoadLessonPort loadLessonPort;
    private final LoadLessonOptionPort loadLessonOptionPort;
    private final FindProfilePort findProfilePort;
    private final SaveOrderPort saveOrderPort;

    @Override
    @Transactional
    public CreateOrderAppResponse createOrder(CreateOrderAppRequest request) {
        Lesson lesson = loadLessonPort.loadLesson(request.getLessonNo());

        loadLessonOptionPort.load(request.getLessonOptionNo());

        Profile profile = findProfilePort.findById(request.getProfileId())
                .orElseThrow(() -> new ProfileNotFoundException(request.getProfileId()));

        List<OrderDiscount> discounts = resolveDiscounts(lesson, profile);

        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .lessonNo(request.getLessonNo())
                .lessonOptionNo(request.getLessonOptionNo())
                .buyer(request.getProfileId())
                .price(lesson.getAmount())
                .paymentId(null)
                .discounts(discounts)
                .status(OrderStatus.PAYMENT_PENDING)
                .build();

        Order saved = saveOrderPort.save(order);
        return new CreateOrderAppResponse(saved.getId());
    }

    private List<OrderDiscount> resolveDiscounts(Lesson lesson, Profile profile) {
        List<LessonDiscount> lessonDiscounts = lesson.getDiscounts();
        if (lessonDiscounts == null || lessonDiscounts.isEmpty()) {
            return List.of();
        }

        List<OrderDiscount> result = new ArrayList<>();

        // SEX 할인: condition이 구매자 sex와 일치하는 경우만 적용
        if (profile.getSex() != null) {
            lessonDiscounts.stream()
                    .filter(d -> d.getType() == DiscountType.SEX)
                    .filter(d -> d.getCondition().equals(profile.getSex().name()))
                    .map(this::toOrderDiscount)
                    .forEach(result::add);
        }

        // EARLYBIRD 할인: 만료되지 않은(condition >= today) 것 중 가장 이른 1건만 적용
        LocalDate today = LocalDate.now();
        lessonDiscounts.stream()
                .filter(d -> d.getType() == DiscountType.EARLYBIRD)
                .filter(d -> !LocalDate.parse(d.getCondition()).isBefore(today))
                .min(Comparator.comparing(LessonDiscount::getCondition))
                .map(this::toOrderDiscount)
                .ifPresent(result::add);

        return result;
    }

    private OrderDiscount toOrderDiscount(LessonDiscount lessonDiscount) {
        return OrderDiscount.builder()
                .discountType(OrderDiscountType.LESSON)
                .discountId(lessonDiscount.getId())
                .amount(lessonDiscount.getAmount())
                .build();
    }
}
