package com.latinhouse.api.order.application.port.out;

import com.latinhouse.api.order.domain.Order;

public interface SaveOrderPort {
    Order save(Order order);
}
