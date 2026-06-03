package com.latinhouse.api.lesson.application.service;

import com.latinhouse.api.lesson.application.port.in.GetLessonsAppRequest;
import com.latinhouse.api.lesson.application.port.in.GetLessonsAppResponse;
import com.latinhouse.api.lesson.application.port.out.LoadLessonsPort;
import com.latinhouse.api.lesson.domain.DiscountType;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.domain.LessonDiscount;
import com.latinhouse.api.lesson.domain.LessonOption;
import com.latinhouse.api.lesson.domain.Region;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetLessonsServiceTest {

    @Mock
    private LoadLessonsPort loadLessonsPort;

    @InjectMocks
    private GetLessonsService service;

    private Lesson buildLesson(boolean isActive, LocalDateTime start, LocalDateTime end, String discountCondition) {
        LessonOption option = LessonOption.builder()
                .id(1L)
                .startDateTime(start)
                .endDateTime(end)
                .region(Region.GANGNAM)
                .build();

        List<LessonDiscount> discounts = discountCondition != null
                ? List.of(LessonDiscount.builder()
                .id(1L)
                .type(DiscountType.EARLYBIRD)
                .condition(discountCondition)
                .amount(new BigDecimal("10000"))
                .build())
                : List.of();

        return Lesson.builder()
                .id(10L)
                .title("살사 초급반")
                .genre(Genre.SALSA)
                .instructorLo("Ab2Cd3Ef")
                .options(List.of(option))
                .amount(new BigDecimal("80000"))
                .discounts(discounts)
                .contacts(List.of())
                .notices(List.of())
                .isActive(isActive)
                .build();
    }

    @Test
    void getLessons_noFilter_returnsAllOptions() {
        Lesson lesson = buildLesson(true, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), null);
        when(loadLessonsPort.loadLessons(null, null, null)).thenReturn(List.of(lesson));

        List<GetLessonsAppResponse> result = service.getLessons(
                GetLessonsAppRequest.builder().build());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getLessonNo()).isEqualTo(10L);
    }

    @Test
    void getLessons_inactiveLesson_statusIsInactive() {
        Lesson lesson = buildLesson(false, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), null);
        when(loadLessonsPort.loadLessons(any(), any(), any())).thenReturn(List.of(lesson));

        List<GetLessonsAppResponse> result = service.getLessons(GetLessonsAppRequest.builder().build());

        assertThat(result.get(0).getStatus()).isEqualTo("INACTIVE");
    }

    @Test
    void getLessons_futureOption_statusIsPending() {
        Lesson lesson = buildLesson(true, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), null);
        when(loadLessonsPort.loadLessons(any(), any(), any())).thenReturn(List.of(lesson));

        List<GetLessonsAppResponse> result = service.getLessons(GetLessonsAppRequest.builder().build());

        assertThat(result.get(0).getStatus()).isEqualTo("PENDING");
    }

    @Test
    void getLessons_ongoingOption_statusIsInProgress() {
        Lesson lesson = buildLesson(true, LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1), null);
        when(loadLessonsPort.loadLessons(any(), any(), any())).thenReturn(List.of(lesson));

        List<GetLessonsAppResponse> result = service.getLessons(GetLessonsAppRequest.builder().build());

        assertThat(result.get(0).getStatus()).isEqualTo("IN_PROGRESS");
    }

    @Test
    void getLessons_pastOption_statusIsDone() {
        Lesson lesson = buildLesson(true, LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1), null);
        when(loadLessonsPort.loadLessons(any(), any(), any())).thenReturn(List.of(lesson));

        List<GetLessonsAppResponse> result = service.getLessons(GetLessonsAppRequest.builder().build());

        assertThat(result.get(0).getStatus()).isEqualTo("DONE");
    }

    @Test
    void getLessons_earlybirdDiscountFuture_returnsDiscount() {
        String futureDate = LocalDate.now().plusDays(5).toString();
        Lesson lesson = buildLesson(true, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), futureDate);
        when(loadLessonsPort.loadLessons(any(), any(), any())).thenReturn(List.of(lesson));

        List<GetLessonsAppResponse> result = service.getLessons(GetLessonsAppRequest.builder().build());

        assertThat(result.get(0).getDiscountCondition()).isEqualTo(futureDate);
        assertThat(result.get(0).getDiscountAmount()).isEqualByComparingTo("10000");
    }

    @Test
    void getLessons_earlybirdDiscountPast_returnsNull() {
        String pastDate = LocalDate.now().minusDays(1).toString();
        Lesson lesson = buildLesson(true, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), pastDate);
        when(loadLessonsPort.loadLessons(any(), any(), any())).thenReturn(List.of(lesson));

        List<GetLessonsAppResponse> result = service.getLessons(GetLessonsAppRequest.builder().build());

        assertThat(result.get(0).getDiscountCondition()).isNull();
        assertThat(result.get(0).getDiscountAmount()).isNull();
    }

    @Test
    void getLessons_withFilters_passesFiltersToPort() {
        when(loadLessonsPort.loadLessons(Region.GANGNAM, "inst1", Genre.SALSA)).thenReturn(List.of());

        service.getLessons(GetLessonsAppRequest.builder()
                .region(Region.GANGNAM)
                .instructor("inst1")
                .genre(Genre.SALSA)
                .build());

        verify(loadLessonsPort).loadLessons(Region.GANGNAM, "inst1", Genre.SALSA);
    }
}
