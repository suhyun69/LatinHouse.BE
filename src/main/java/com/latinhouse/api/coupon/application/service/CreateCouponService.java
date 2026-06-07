package com.latinhouse.api.coupon.application.service;

import com.latinhouse.api.common.exception.CouponTemplateNotFoundException;
import com.latinhouse.api.coupon.application.port.in.CreateCouponAppRequest;
import com.latinhouse.api.coupon.application.port.in.CreateCouponUseCase;
import com.latinhouse.api.coupon.application.port.out.LoadCouponTemplatePort;
import com.latinhouse.api.coupon.application.port.out.SaveCouponPort;
import com.latinhouse.api.coupon.domain.Coupon;
import com.latinhouse.api.coupon.domain.CouponStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateCouponService implements CreateCouponUseCase {

    private final LoadCouponTemplatePort loadCouponTemplatePort;
    private final SaveCouponPort saveCouponPort;

    @Override
    @Transactional
    public void createCoupon(CreateCouponAppRequest request) {
        loadCouponTemplatePort.findById(request.getTemplateId())
                .orElseThrow(() -> new CouponTemplateNotFoundException(request.getTemplateId()));

        List<Coupon> coupons = new ArrayList<>();
        for (int i = 0; i < request.getCount(); i++) {
            coupons.add(Coupon.builder()
                    .templateId(request.getTemplateId())
                    .owner(null)
                    .status(CouponStatus.AVAILABLE)
                    .build());
        }

        saveCouponPort.saveAll(coupons);
    }
}
