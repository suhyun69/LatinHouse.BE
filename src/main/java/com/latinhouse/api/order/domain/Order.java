package com.latinhouse.api.order.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class Order {
    private String id;
    private Long lessonNo;
    private Long lessonOptionNo;
    private String buyer;
    private BigDecimal price;
    private Long paymentId;
    private List<OrderDiscount> discounts;
    private OrderStatus status;
}
