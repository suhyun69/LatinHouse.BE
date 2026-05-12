package com.latinhouse.api.order.adapter.out.persistence.mapper;

import com.latinhouse.api.order.adapter.out.persistence.entity.OrderJpaEntity;
import com.latinhouse.api.order.domain.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderJpaEntity mapToJpaEntity(Order order) {
        return OrderJpaEntity.builder()
                .no(order.getNo())
                .lessonNo(order.getLessonNo())
                .optionNo(order.getOptionNo())
                .profileId(order.getProfileId())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .createdBy(order.getCreatedBy())
                .modifiedAt(order.getModifiedAt())
                .modifiedBy(order.getModifiedBy())
                .build();
    }

    public Order mapToDomainEntity(OrderJpaEntity entity) {
        return Order.builder()
                .no(entity.getNo())
                .lessonNo(entity.getLessonNo())
                .optionNo(entity.getOptionNo())
                .profileId(entity.getProfileId())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .modifiedAt(entity.getModifiedAt())
                .modifiedBy(entity.getModifiedBy())
                .build();
    }
}
