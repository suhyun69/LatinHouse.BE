package com.latinhouse.api.order.port.out;

import com.latinhouse.api.order.domain.Order;

public interface CreateOrderPort {
    Order create(Order order);
}
