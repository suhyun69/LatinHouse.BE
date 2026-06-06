package com.latinhouse.api.order.application.port.in;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class GetOrdersAppResponse {
    private final String orderId;
    private final Long lessonNo;
    private final Long lessonOptionNo;
    private final BigDecimal price;
    private final String status;
    private final List<DiscountInfo> discounts;

    @Getter
    @Builder
    public static class DiscountInfo {
        private final String discountType;
        private final Long discountId;
        private final BigDecimal amount;
    }
}
