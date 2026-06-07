package com.latinhouse.api.coupon.application.port.out;

import com.latinhouse.api.coupon.domain.Coupon;

import java.util.Optional;

public interface LoadCouponPort {
    Optional<Coupon> findCouponById(Long couponId);
}
