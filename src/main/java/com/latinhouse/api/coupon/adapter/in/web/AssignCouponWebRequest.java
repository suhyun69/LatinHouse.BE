package com.latinhouse.api.coupon.adapter.in.web;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AssignCouponWebRequest {

    @NotNull
    private Long couponId;
}
