package com.latinhouse.backend.checkout.adapter.out.persistence;

import com.latinhouse.backend.checkout.adapter.out.persistence.entity.OrderJpaEntity;
import com.latinhouse.backend.checkout.adapter.out.persistence.mapper.OrderJpaMapper;
import com.latinhouse.backend.checkout.adapter.out.persistence.repository.OrderJpaRepository;
import com.latinhouse.backend.checkout.domain.Order;
import com.latinhouse.backend.checkout.port.in.request.EnrollLessonAppRequest;
import com.latinhouse.backend.checkout.port.out.CreateOrderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckoutPersistenceAdapter implements
        CreateOrderPort
{
    private final OrderJpaMapper orderJpaMapper;
    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order createOrder(EnrollLessonAppRequest appReq) {
        OrderJpaEntity orderT_ = orderJpaMapper.mapToJpaEntity(appReq);
        OrderJpaEntity orderT = orderJpaRepository.save(orderT_);
        return orderJpaMapper.mapToDomainEntity(orderT);
    }
}
