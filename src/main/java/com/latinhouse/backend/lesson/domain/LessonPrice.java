package com.latinhouse.backend.lesson.domain;

import com.latinhouse.backend.profile.domain.Sex;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value // 객체 불변(Immutable) => @Setter 생성되지 않음
@Builder // mapToDomainEntity
public class LessonPrice {
    private Sex sex;
    private BigDecimal price;
}