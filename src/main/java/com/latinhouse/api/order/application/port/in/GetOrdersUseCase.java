package com.latinhouse.api.order.application.port.in;

import java.util.List;

public interface GetOrdersUseCase {
    List<GetOrdersAppResponse> getOrders(GetOrdersAppRequest request);
}
