package com.latinhouse.api.order.application.port.in;

public interface CreateOrderUseCase {
    CreateOrderAppResponse createOrder(CreateOrderAppRequest request);
}
