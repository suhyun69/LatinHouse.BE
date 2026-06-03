package com.latinhouse.api.order.application.port.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateOrderAppResponse {
    private final String orderId;
}
