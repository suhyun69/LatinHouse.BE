package com.latinhouse.api.common.exception;

public class CouponNotFoundException extends RuntimeException {

    public CouponNotFoundException(Long couponId) {
        super("Coupon not found: " + couponId);
    }
}
