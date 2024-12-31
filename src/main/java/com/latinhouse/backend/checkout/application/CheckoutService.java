package com.latinhouse.backend.checkout.application;

import com.latinhouse.backend.checkout.domain.Order;
import com.latinhouse.backend.checkout.port.in.EnrollLessonUseCase;
import com.latinhouse.backend.checkout.port.in.request.EnrollLessonAppRequest;
import com.latinhouse.backend.checkout.port.in.response.EnrollLessonAppResponse;
import com.latinhouse.backend.checkout.port.out.CreateOrderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutService implements
        EnrollLessonUseCase
{
    private final CreateOrderPort createOrderPort;

    private final KafkaTemplate<String, EnrollLessonAppRequest> kafkaTemplate;

    @Override
    public void enqueueKafka(EnrollLessonAppRequest appReq) {
        kafkaTemplate.send("test-topic", appReq);
    }

    @Override
    public void enrollLesson(EnrollLessonAppRequest appReq) {
        Order order = createOrderPort.createOrder(appReq);
    }
}
