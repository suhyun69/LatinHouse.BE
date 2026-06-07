package com.latinhouse.api.coupon.application.service;

import com.latinhouse.api.coupon.application.port.in.CreateCouponTemplateAppRequest;
import com.latinhouse.api.coupon.application.port.in.CreateCouponTemplateAppResponse;
import com.latinhouse.api.coupon.application.port.in.CreateCouponTemplateUseCase;
import com.latinhouse.api.coupon.application.port.out.SaveCouponTemplatePort;
import com.latinhouse.api.coupon.domain.CouponTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCouponTemplateService implements CreateCouponTemplateUseCase {

    private final SaveCouponTemplatePort saveCouponTemplatePort;

    @Override
    @Transactional
    public CreateCouponTemplateAppResponse createCouponTemplate(CreateCouponTemplateAppRequest request) {
        CouponTemplate couponTemplate = CouponTemplate.builder()
                .title(request.getTitle())
                .type(request.getType())
                .target(request.getTarget())
                .amount(request.getAmount())
                .build();

        CouponTemplate saved = saveCouponTemplatePort.save(couponTemplate);
        return new CreateCouponTemplateAppResponse(saved.getId());
    }
}
