package com.latinhouse.api.coupon.application.service;

import com.latinhouse.api.common.exception.CouponNotFoundException;
import com.latinhouse.api.common.exception.ProfileNotFoundException;
import com.latinhouse.api.coupon.application.port.in.AssignCouponAppRequest;
import com.latinhouse.api.coupon.application.port.in.AssignCouponAppResponse;
import com.latinhouse.api.coupon.application.port.in.AssignCouponUseCase;
import com.latinhouse.api.coupon.application.port.out.LoadCouponPort;
import com.latinhouse.api.coupon.application.port.out.UpdateCouponPort;
import com.latinhouse.api.coupon.domain.Coupon;
import com.latinhouse.api.profile.application.port.out.FindProfilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssignCouponService implements AssignCouponUseCase {

    private final FindProfilePort findProfilePort;
    private final LoadCouponPort loadCouponPort;
    private final UpdateCouponPort updateCouponPort;

    @Override
    @Transactional
    public AssignCouponAppResponse assignCoupon(AssignCouponAppRequest request) {
        findProfilePort.findById(request.getProfileId())
                .orElseThrow(() -> new ProfileNotFoundException(request.getProfileId()));

        Coupon coupon = loadCouponPort.findCouponById(request.getCouponId())
                .orElseThrow(() -> new CouponNotFoundException(request.getCouponId()));

        Coupon updated = Coupon.builder()
                .id(coupon.getId())
                .templateId(coupon.getTemplateId())
                .owner(request.getProfileId())
                .status(coupon.getStatus())
                .build();

        Coupon saved = updateCouponPort.update(updated);
        return new AssignCouponAppResponse(saved.getId());
    }
}
