package com.latinhouse.api.coupon.application.port.in;

import com.latinhouse.api.coupon.domain.CouponTemplateType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CreateCouponTemplateAppRequest {
    private String title;
    private CouponTemplateType type;
    private Long target;
    private BigDecimal amount;
}
