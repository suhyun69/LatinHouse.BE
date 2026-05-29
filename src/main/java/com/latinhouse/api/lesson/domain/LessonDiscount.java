package com.latinhouse.api.lesson.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class LessonDiscount {
    private final Long id;
    private final DiscountType type;
    private final String condition;
    private final BigDecimal amount;
}
