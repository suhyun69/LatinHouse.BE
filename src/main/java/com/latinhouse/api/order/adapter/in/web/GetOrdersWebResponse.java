package com.latinhouse.api.order.adapter.in.web;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class GetOrdersWebResponse {
    private final String orderId;
    private final Long lessonNo;
    private final Long lessonOptionNo;
    private final BigDecimal price;
    private final String status;
    private final List<OrderDiscountInfo> discounts;

    @Getter
    @Builder
    public static class OrderDiscountInfo {
        private final String discountType;
        private final Long discountId;
        private final BigDecimal amount;
    }
}
