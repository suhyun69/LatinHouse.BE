package com.latinhouse.api.coupon.adapter.out.persistence;

import com.latinhouse.api.coupon.application.port.out.LoadCouponTemplatePort;
import com.latinhouse.api.coupon.application.port.out.SaveCouponPort;
import com.latinhouse.api.coupon.application.port.out.SaveCouponTemplatePort;
import com.latinhouse.api.coupon.domain.Coupon;
import com.latinhouse.api.coupon.domain.CouponTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class CouponPersistenceAdapter implements SaveCouponTemplatePort, LoadCouponTemplatePort, SaveCouponPort {

    private final CouponTemplateJpaRepository couponTemplateJpaRepository;
    private final CouponJpaRepository couponJpaRepository;

    @Override
    public CouponTemplate save(CouponTemplate couponTemplate) {
        CouponTemplateEntity entity = CouponPersistenceMapper.toEntity(couponTemplate);
        CouponTemplateEntity saved = couponTemplateJpaRepository.save(entity);
        return CouponPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<CouponTemplate> findById(Long templateId) {
        return couponTemplateJpaRepository.findById(templateId)
                .map(CouponPersistenceMapper::toDomain);
    }

    @Override
    public void saveAll(List<Coupon> coupons) {
        List<CouponEntity> entities = coupons.stream()
                .map(CouponPersistenceMapper::toCouponEntity)
                .toList();
        couponJpaRepository.saveAll(entities);
    }
}
