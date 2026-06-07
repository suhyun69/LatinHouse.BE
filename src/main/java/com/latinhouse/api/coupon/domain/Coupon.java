package com.latinhouse.api.coupon.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Coupon {
    private Long id;
    private Long templateId;
    private String owner;
    private CouponStatus status;
}
