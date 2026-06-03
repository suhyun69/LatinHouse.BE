package com.latinhouse.api.order.adapter.in.web;

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
    private Long lessonOptionNo;

    @NotBlank
    private String profileId;
}
