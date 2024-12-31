package com.latinhouse.backend.checkout.port.out;

import com.latinhouse.backend.checkout.domain.Order;
import com.latinhouse.backend.checkout.port.in.request.EnrollLessonAppRequest;

public interface CreateOrderPort {
    Order createOrder(EnrollLessonAppRequest appReq);

}
