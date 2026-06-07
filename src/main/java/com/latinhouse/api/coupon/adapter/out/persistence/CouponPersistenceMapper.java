package com.latinhouse.api.coupon.adapter.out.persistence;

import com.latinhouse.api.coupon.domain.Coupon;
import com.latinhouse.api.coupon.domain.CouponStatus;
import com.latinhouse.api.coupon.domain.CouponTemplate;
import com.latinhouse.api.coupon.domain.CouponTemplateType;

class CouponPersistenceMapper {

    private CouponPersistenceMapper() {}

    static CouponTemplateEntity toEntity(CouponTemplate couponTemplate) {
        return CouponTemplateEntity.builder()
                .id(couponTemplate.getId())
                .title(couponTemplate.getTitle())
                .type(couponTemplate.getType().name())
                .target(couponTemplate.getTarget())
                .amount(couponTemplate.getAmount())
                .build();
    }

    static CouponTemplate toDomain(CouponTemplateEntity entity) {
        return CouponTemplate.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .type(CouponTemplateType.valueOf(entity.getType()))
                .target(entity.getTarget())
                .amount(entity.getAmount())
                .build();
    }

    static CouponEntity toCouponEntity(Coupon coupon) {
        return CouponEntity.builder()
                .id(coupon.getId())
                .templateId(coupon.getTemplateId())
                .owner(coupon.getOwner())
                .status(coupon.getStatus().name())
                .build();
    }

    static Coupon toCouponDomain(CouponEntity entity) {
        return Coupon.builder()
                .id(entity.getId())
                .templateId(entity.getTemplateId())
                .owner(entity.getOwner())
                .status(CouponStatus.valueOf(entity.getStatus()))
                .build();
    }
}
