package com.latinhouse.api.order.adapter.in.web.response;

import com.latinhouse.api.order.port.in.response.OrderAppResponse;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderWebResponse {

    private final Long no;
    private final Long lessonNo;
    private final Long optionNo;
    private final String profileId;
    private final String status;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;

    public OrderWebResponse(OrderAppResponse appResponse) {
        this.no = appResponse.getNo();
        this.lessonNo = appResponse.getLessonNo();
        this.optionNo = appResponse.getOptionNo();
        this.profileId = appResponse.getProfileId();
        this.status = appResponse.getStatus();
        this.createdAt = appResponse.getCreatedAt();
        this.createdBy = appResponse.getCreatedBy();
        this.modifiedAt = appResponse.getModifiedAt();
        this.modifiedBy = appResponse.getModifiedBy();
    }
}
