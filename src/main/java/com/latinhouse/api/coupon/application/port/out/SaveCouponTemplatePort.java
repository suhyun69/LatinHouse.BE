package com.latinhouse.api.coupon.application.port.out;

import com.latinhouse.api.coupon.domain.CouponTemplate;

public interface SaveCouponTemplatePort {
    CouponTemplate save(CouponTemplate couponTemplate);
}
