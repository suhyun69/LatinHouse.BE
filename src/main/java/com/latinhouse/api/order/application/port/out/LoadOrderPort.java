package com.latinhouse.api.order.application.port.out;

import com.latinhouse.api.order.domain.Order;

import java.util.List;

public interface LoadOrderPort {
    List<Order> loadOrders(String buyer, Long lessonNo);
}
