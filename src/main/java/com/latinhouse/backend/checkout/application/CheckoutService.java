package com.latinhouse.backend.checkout.application;

import com.latinhouse.backend.checkout.domain.Order;
import com.latinhouse.backend.checkout.port.in.EnrollLessonUseCase;
import com.latinhouse.backend.checkout.port.in.request.EnrollLessonAppRequest;
import com.latinhouse.backend.checkout.port.in.response.EnrollLessonAppResponse;
import com.latinhouse.backend.checkout.port.out.CreateOrderPort;
import com.latinhouse.backend.profile.domain.Profile;
import com.latinhouse.backend.profile.port.in.response.CreateProfileAppResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutService implements
        EnrollLessonUseCase
{
    private final CreateOrderPort createOrderPort;

    @Override
    public EnrollLessonAppResponse enrollLesson(EnrollLessonAppRequest appReq) {
        Order order = createOrderPort.createOrder(appReq);
        return new EnrollLessonAppResponse(order);
    }
}
