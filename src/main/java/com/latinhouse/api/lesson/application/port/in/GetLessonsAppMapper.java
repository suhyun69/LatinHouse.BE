package com.latinhouse.api.lesson.application.port.in;

import com.latinhouse.api.lesson.domain.DiscountType;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.domain.LessonDiscount;
import com.latinhouse.api.lesson.domain.LessonOption;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class GetLessonsAppMapper {

    private GetLessonsAppMapper() {}

    public static List<GetLessonsAppResponse> toResponseList(List<Lesson> lessons) {
        return lessons.stream()
                .flatMap(lesson -> lesson.getOptions().stream()
                        .map(option -> toResponse(lesson, option)))
                .toList();
    }

    private static GetLessonsAppResponse toResponse(Lesson lesson, LessonOption option) {
        LessonDiscount earlybird = findEarlybird(lesson.getDiscounts());
        return GetLessonsAppResponse.builder()
                .optionId(option.getId())
                .lessonNo(lesson.getId())
                .instructorLo(lesson.getInstructorLo())
                .instructorLa(lesson.getInstructorLa())
                .title(lesson.getTitle())
                .genre(lesson.getGenre().getCode())
                .startDate(option.getStartDateTime().toLocalDate().toString())
                .startTime(option.getStartDateTime().toLocalTime().toString().substring(0, 5))
                .endDate(option.getEndDateTime().toLocalDate().toString())
                .endTime(option.getEndDateTime().toLocalTime().toString().substring(0, 5))
                .region(option.getRegion().getCode())
                .price(lesson.getAmount())
                .discountCondition(earlybird != null ? earlybird.getCondition() : null)
                .discountAmount(earlybird != null ? earlybird.getAmount() : null)
                .status(calcStatus(lesson.isActive(), option.getStartDateTime(), option.getEndDateTime()))
                .build();
    }

    private static String calcStatus(boolean isActive, LocalDateTime start, LocalDateTime end) {
        if (!isActive) return "INACTIVE";
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(start)) return "PENDING";
        if (!now.isAfter(end)) return "IN_PROGRESS";
        return "DONE";
    }

    private static LessonDiscount findEarlybird(List<LessonDiscount> discounts) {
        if (discounts == null) return null;
        LocalDate today = LocalDate.now();
        return discounts.stream()
                .filter(d -> d.getType() == DiscountType.EARLYBIRD)
                .filter(d -> {
                    try {
                        return !LocalDate.parse(d.getCondition()).isBefore(today);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .min(Comparator.comparing(LessonDiscount::getCondition))
                .orElse(null);
    }
}
