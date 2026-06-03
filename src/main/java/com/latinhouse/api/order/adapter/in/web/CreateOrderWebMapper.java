package com.latinhouse.api.order.adapter.in.web;

import com.latinhouse.api.order.application.port.in.CreateOrderAppRequest;
import com.latinhouse.api.order.application.port.in.CreateOrderAppResponse;

public class CreateOrderWebMapper {

    private CreateOrderWebMapper() {}

    public static CreateOrderAppRequest toAppRequest(CreateOrderWebRequest webRequest) {
        return CreateOrderAppRequest.builder()
                .lessonNo(webRequest.getLessonNo())
                .lessonOptionNo(webRequest.getLessonOptionNo())
                .profileId(webRequest.getProfileId())
                .build();
    }

    public static CreateOrderWebResponse toWebResponse(CreateOrderAppResponse appResponse) {
        return new CreateOrderWebResponse(appResponse.getOrderId());
    }
}
