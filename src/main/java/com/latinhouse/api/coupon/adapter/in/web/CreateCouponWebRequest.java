package com.latinhouse.api.coupon.adapter.in.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCouponWebRequest {

    @NotNull
    private Long templateId;

    @NotNull
    @Min(1)
    private Integer count;
}
