package com.latinhouse.api.lesson.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class Lesson {
    private final Long id;
    private final String title;
    private final Genre genre;
    private final String instructorLo;
    private final String instructorLa;
    private final List<LessonOption> options;
    private final BigDecimal amount;
    private final List<LessonDiscount> discounts;
    private final LessonAccount account;
    private final List<LessonContact> contacts;
    private final boolean isActive;
    private final List<LessonNotice> notices;
}
