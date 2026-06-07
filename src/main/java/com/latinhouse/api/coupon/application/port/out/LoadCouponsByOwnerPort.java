package com.latinhouse.api.coupon.application.port.out;

import com.latinhouse.api.coupon.domain.Coupon;

import java.util.List;

public interface LoadCouponsByOwnerPort {
    List<Coupon> findByOwner(String owner);
}
