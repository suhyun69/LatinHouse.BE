package com.latinhouse.api.order.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderDiscount {
    private Long id;
    private String orderId;
    private OrderDiscountType discountType;
    private Long discountId;
    private BigDecimal amount;
}
