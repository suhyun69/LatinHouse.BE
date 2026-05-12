package com.latinhouse.api.order.port.in.request;

import com.latinhouse.api.order.adapter.in.web.request.CreateOrderWebRequest;
import com.latinhouse.api.order.domain.OrderStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateOrderAppRequest {

    Long lessonNo;
    Long optionNo;
    String profileId;
    OrderStatus status;

    public static CreateOrderAppRequest from(CreateOrderWebRequest webReq) {
        return CreateOrderAppRequest.builder()
                .lessonNo(webReq.getLessonNo())
                .optionNo(webReq.getOptionNo())
                .profileId(webReq.getProfileId())
                .status(OrderStatus.of(webReq.getStatus()))
                .build();
    }
}
