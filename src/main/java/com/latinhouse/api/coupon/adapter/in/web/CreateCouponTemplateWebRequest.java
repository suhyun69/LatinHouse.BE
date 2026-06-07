package com.latinhouse.api.coupon.adapter.in.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CreateCouponTemplateWebRequest {

    @NotBlank
    private String title;

    @NotNull
    private String type;

    @NotNull
    private Long target;

    @NotNull
    private BigDecimal amount;
}
