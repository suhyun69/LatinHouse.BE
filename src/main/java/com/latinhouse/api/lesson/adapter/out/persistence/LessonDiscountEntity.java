package com.latinhouse.api.lesson.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "lesson_discount")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LessonDiscountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false, length = 1)
    private String type;

    @Column(name = "condition_value", nullable = false)
    private String conditionValue;

    @Column(name = "amount", precision = 15, scale = 2)
    private BigDecimal amount;

    @Builder
    public LessonDiscountEntity(Long id, String type, String conditionValue, BigDecimal amount) {
        this.id = id;
        this.type = type;
        this.conditionValue = conditionValue;
        this.amount = amount;
    }
}
