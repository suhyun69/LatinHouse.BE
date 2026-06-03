package com.latinhouse.api.order.adapter.out.persistence;

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
@Table(name = "order_discount")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDiscountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false, length = 36)
    private String orderId;

    @Column(name = "discount_type", nullable = false, length = 20)
    private String discountType;

    @Column(name = "discount_id")
    private Long discountId;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Builder
    public OrderDiscountEntity(Long id, String orderId, String discountType, Long discountId, BigDecimal amount) {
        this.id = id;
        this.orderId = orderId;
        this.discountType = discountType;
        this.discountId = discountId;
        this.amount = amount;
    }
}
