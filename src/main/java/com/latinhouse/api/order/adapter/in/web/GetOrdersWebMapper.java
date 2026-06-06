package com.latinhouse.api.order.adapter.in.web;

import com.latinhouse.api.order.application.port.in.GetOrdersAppRequest;
import com.latinhouse.api.order.application.port.in.GetOrdersAppResponse;

import java.util.List;

public class GetOrdersWebMapper {

    private GetOrdersWebMapper() {}

    public static GetOrdersAppRequest toAppRequest(String buyer, String lessonNo) {
        return GetOrdersAppRequest.builder()
                .buyer(buyer)
                .lessonNo(lessonNo != null ? Long.parseLong(lessonNo) : null)
                .build();
    }

    public static List<GetOrdersWebResponse> toWebResponseList(List<GetOrdersAppResponse> appResponses) {
        return appResponses.stream()
                .map(GetOrdersWebMapper::toWebResponse)
                .toList();
    }

    private static GetOrdersWebResponse toWebResponse(GetOrdersAppResponse appResponse) {
        List<GetOrdersWebResponse.OrderDiscountInfo> discounts = appResponse.getDiscounts() == null ? List.of() :
                appResponse.getDiscounts().stream()
                        .map(d -> GetOrdersWebResponse.OrderDiscountInfo.builder()
                                .discountType(d.getDiscountType())
                                .discountId(d.getDiscountId())
                                .amount(d.getAmount())
                                .build())
                        .toList();

        return GetOrdersWebResponse.builder()
                .orderId(appResponse.getOrderId())
                .lessonNo(appResponse.getLessonNo())
                .lessonOptionNo(appResponse.getLessonOptionNo())
                .price(appResponse.getPrice())
                .status(appResponse.getStatus())
                .discounts(discounts)
                .build();
    }
}
