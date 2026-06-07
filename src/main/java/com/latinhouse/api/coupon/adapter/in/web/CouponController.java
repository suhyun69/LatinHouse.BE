package com.latinhouse.api.coupon.adapter.in.web;

import com.latinhouse.api.coupon.application.port.in.CreateCouponTemplateUseCase;
import com.latinhouse.api.coupon.application.port.in.CreateCouponUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Coupon", description = "쿠폰 관리 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CouponController {

    private final CreateCouponTemplateUseCase createCouponTemplateUseCase;
    private final CreateCouponUseCase createCouponUseCase;

    @Operation(summary = "쿠폰 템플릿 생성", description = "쿠폰 템플릿을 생성합니다.")
    @PostMapping("/coupon/template")
    public ResponseEntity<CreateCouponTemplateWebResponse> createCouponTemplate(
            @Valid @RequestBody CreateCouponTemplateWebRequest request) {
        CreateCouponTemplateWebResponse response = CouponWebMapper.toWebResponse(
                createCouponTemplateUseCase.createCouponTemplate(
                        CouponWebMapper.toAppRequest(request)
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "쿠폰 일괄 발행", description = "쿠폰 템플릿 기반으로 쿠폰을 count 개수만큼 발행합니다.")
    @PostMapping("/coupon")
    public ResponseEntity<Void> createCoupon(
            @Valid @RequestBody CreateCouponWebRequest request) {
        createCouponUseCase.createCoupon(
                CouponWebMapper.toAppRequest(request)
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
