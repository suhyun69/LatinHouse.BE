package com.latinhouse.api.coupon.adapter.in.web;

import com.latinhouse.api.coupon.application.port.in.CreateCouponAppRequest;
import com.latinhouse.api.coupon.application.port.in.CreateCouponTemplateAppRequest;
import com.latinhouse.api.coupon.application.port.in.CreateCouponTemplateAppResponse;
import com.latinhouse.api.coupon.domain.CouponTemplateType;

class CouponWebMapper {

    private CouponWebMapper() {}

    static CreateCouponTemplateAppRequest toAppRequest(CreateCouponTemplateWebRequest webRequest) {
        return CreateCouponTemplateAppRequest.builder()
                .title(webRequest.getTitle())
                .type(CouponTemplateType.valueOf(webRequest.getType()))
                .target(webRequest.getTarget())
                .amount(webRequest.getAmount())
                .build();
    }

    static CreateCouponTemplateWebResponse toWebResponse(CreateCouponTemplateAppResponse appResponse) {
        return new CreateCouponTemplateWebResponse(String.valueOf(appResponse.getCouponTemplateId()));
    }

    static CreateCouponAppRequest toAppRequest(CreateCouponWebRequest webRequest) {
        return CreateCouponAppRequest.builder()
                .templateId(webRequest.getTemplateId())
                .count(webRequest.getCount())
                .build();
    }
}
