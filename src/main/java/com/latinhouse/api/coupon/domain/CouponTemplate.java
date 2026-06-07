package com.latinhouse.api.coupon.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CouponTemplate {
    private Long id;
    private String title;
    private CouponTemplateType type;
    private Long target;
    private BigDecimal amount;
}
