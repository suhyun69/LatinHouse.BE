package com.latinhouse.api.order.port.in.response;

import com.latinhouse.api.order.domain.Order;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderAppResponse {

    private final Long no;
    private final Long lessonNo;
    private final Long optionNo;
    private final String profileId;
    private final String status;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;

    public OrderAppResponse(Order order) {
        this.no = order.getNo();
        this.lessonNo = order.getLessonNo();
        this.optionNo = order.getOptionNo();
        this.profileId = order.getProfileId();
        this.status = order.getStatus() != null ? order.getStatus().name() : null;
        this.createdAt = order.getCreatedAt();
        this.createdBy = order.getCreatedBy();
        this.modifiedAt = order.getModifiedAt();
        this.modifiedBy = order.getModifiedBy();
    }
}
