package com.latinhouse.api.common.exception;

public class CouponTemplateNotFoundException extends RuntimeException {

    public CouponTemplateNotFoundException(Long templateId) {
        super("CouponTemplate not found: " + templateId);
    }
}
