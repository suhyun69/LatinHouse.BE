package com.latinhouse.api.lesson.application.port.in;

import com.latinhouse.api.lesson.domain.DiscountType;
import org.junit.jupiter.api.RepeatedTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CreateRandomLessonAppMapperTest {

    private static final Set<BigDecimal> VALID_AMOUNTS = Set.of(
            new BigDecimal("30000"), new BigDecimal("50000"),
            new BigDecimal("80000"), new BigDecimal("100000")
    );
    private static final Set<BigDecimal> VALID_DISCOUNT_AMOUNTS = Set.of(
            new BigDecimal("5000"), new BigDecimal("10000"), new BigDecimal("15000")
    );

    @RepeatedTest(50)
    void toCreateLessonAppRequest_generatesValidFields() {
        CreateLessonAppRequest req = CreateRandomLessonAppMapper.toCreateLessonAppRequest("loId", "laId");

        // genre
        assertThat(req.getGenre()).isNotNull();
        assertThat(req.getGenre().getCode()).isIn("S", "B");

        // title
        assertThat(req.getTitle()).isNotBlank();

        // instructorLo, La
        assertThat(req.getInstructorLo()).isEqualTo("loId");
        assertThat(req.getInstructorLa()).isEqualTo("laId");

        // options 1~3개
        List<CreateLessonAppRequest.OptionAppReq> options = req.getOptions();
        assertThat(options).hasSizeBetween(1, 3);

        LocalDate today = LocalDate.now();
        LocalDate earliestStartDate = null;

        for (CreateLessonAppRequest.OptionAppReq option : options) {
            LocalDateTime start = option.getStartDateTime();
            LocalDateTime end = option.getEndDateTime();

            // endDateTime > startDateTime
            assertThat(end).isAfter(start);

            // startDate 오늘+7 ~ 오늘+60
            LocalDate startDate = start.toLocalDate();
            assertThat(startDate).isAfterOrEqualTo(today.plusDays(7));
            assertThat(startDate).isBeforeOrEqualTo(today.plusDays(60));

            // startTime in {10:00, 14:00, 19:00, 20:00}
            assertThat(start.getMinute()).isEqualTo(0);
            assertThat(start.getHour()).isIn(10, 14, 19, 20);

            // endTime = startTime + 2h
            assertThat(end).isEqualTo(start.plusHours(2));

            // region
            assertThat(option.getRegion()).isNotNull();
            assertThat(option.getRegion().getCode()).isIn("GN", "HD");

            if (earliestStartDate == null || startDate.isBefore(earliestStartDate)) {
                earliestStartDate = startDate;
            }
        }

        // amount
        assertThat(VALID_AMOUNTS).contains(req.getAmount());

        // discounts 0~2개
        List<CreateLessonAppRequest.DiscountAppReq> discounts = req.getDiscounts();
        assertThat(discounts).hasSizeBetween(0, 2);

        for (CreateLessonAppRequest.DiscountAppReq discount : discounts) {
            assertThat(VALID_DISCOUNT_AMOUNTS).contains(discount.getAmount());

            if (discount.getType() == DiscountType.EARLYBIRD) {
                LocalDate conditionDate = LocalDate.parse(discount.getCondition());
                assertThat(conditionDate).isEqualTo(earliestStartDate.minusDays(7));
            } else if (discount.getType() == DiscountType.SEX) {
                assertThat(discount.getCondition()).isIn("M", "F");
            }
        }

        // isActive = true
        assertThat(req.getIsActive()).isTrue();
    }
}
