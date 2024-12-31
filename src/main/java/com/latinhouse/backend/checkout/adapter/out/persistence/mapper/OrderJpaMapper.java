package com.latinhouse.backend.checkout.adapter.out.persistence.mapper;

import com.latinhouse.backend.checkout.adapter.out.persistence.entity.OrderJpaEntity;
import com.latinhouse.backend.checkout.domain.Order;
import com.latinhouse.backend.checkout.port.in.request.EnrollLessonAppRequest;
import com.latinhouse.backend.profile.adapter.out.persistence.entity.ProfileJpaEntity;
import com.latinhouse.backend.profile.domain.Profile;
import com.latinhouse.backend.profile.domain.Sex;
import com.latinhouse.backend.profile.port.in.request.CreateProfileAppRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderJpaMapper {

    public OrderJpaEntity mapToJpaEntity(EnrollLessonAppRequest appReq) {
        return OrderJpaEntity.builder()
                .lessonNo(appReq.getLessonNo())
                .profileId(appReq.getProfileId())
                .build();
    }

    public Order mapToDomainEntity(OrderJpaEntity orderT) {
        return Order.builder()
                .seq(orderT.getSeq())
                .lessonNo(orderT.getLessonNo())
                .profileId(orderT.getProfileId())
                .build();
    }
}
