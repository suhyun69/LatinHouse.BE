package com.latinhouse.api.order.port.in;

import com.latinhouse.api.order.port.in.request.CreateOrderAppRequest;
import com.latinhouse.api.order.port.in.response.OrderAppResponse;

public interface CreateOrderUseCase {
    OrderAppResponse create(CreateOrderAppRequest appReq);
}
