package com.latinhouse.backend.lesson.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value // 객체 불변(Immutable) => @Setter 생성되지 않음
@Builder // mapToDomainEntity
public class LessonDiscount {
    private DiscountType type;
    private String condition;
    private BigDecimal amount;
}