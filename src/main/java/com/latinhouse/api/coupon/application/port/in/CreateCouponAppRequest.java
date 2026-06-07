package com.latinhouse.api.coupon.application.port.in;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCouponAppRequest {
    private Long templateId;
    private Integer count;
}
