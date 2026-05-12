package com.latinhouse.api.order.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Order {

    private final Long no;
    private final Long lessonNo;
    private final Long optionNo;
    private final String profileId;
    private final OrderStatus status;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;
}
