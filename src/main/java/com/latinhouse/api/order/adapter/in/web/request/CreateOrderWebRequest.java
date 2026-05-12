package com.latinhouse.api.order.adapter.in.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateOrderWebRequest {

    @NotNull
    private Long lessonNo;

    @NotNull
    private Long optionNo;

    @NotBlank
    private String profileId;

    @NotBlank
    private String status;
}
