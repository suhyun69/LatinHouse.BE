package com.latinhouse.api.order.adapter.in.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateOrderWebResponse {
    private final String orderId;
}
