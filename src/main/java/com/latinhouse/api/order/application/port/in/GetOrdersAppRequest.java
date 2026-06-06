package com.latinhouse.api.order.application.port.in;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetOrdersAppRequest {
    private final String buyer;
    private final Long lessonNo;
}
