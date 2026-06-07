package com.latinhouse.api.coupon.application.port.out;

import com.latinhouse.api.coupon.domain.CouponTemplate;

import java.util.Optional;

public interface LoadCouponTemplatePort {
    Optional<CouponTemplate> findById(Long templateId);
}
