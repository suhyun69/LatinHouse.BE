package com.latinhouse.api.order.application.port.in;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateOrderAppRequest {
    private Long lessonNo;
    private Long lessonOptionNo;
    private String profileId;
}
