package com.latinhouse.api.coupon.application.port.out;

import com.latinhouse.api.coupon.domain.Coupon;

public interface UpdateCouponPort {
    Coupon update(Coupon coupon);
}
