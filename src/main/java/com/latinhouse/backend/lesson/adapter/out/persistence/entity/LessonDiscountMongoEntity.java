package com.latinhouse.backend.lesson.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder // mapToJpaEntity
@NoArgsConstructor
@AllArgsConstructor
public class LessonDiscountMongoEntity {
    private String type;
    private String condition;
    private BigDecimal amount;
}