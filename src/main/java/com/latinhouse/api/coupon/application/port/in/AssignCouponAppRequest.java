package com.latinhouse.api.coupon.application.port.in;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AssignCouponAppRequest {
    private String profileId;
    private Long couponId;
}
