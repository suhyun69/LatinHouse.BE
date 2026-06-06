package com.latinhouse.api.order.application.service;

import com.latinhouse.api.order.application.port.in.GetOrdersAppRequest;
import com.latinhouse.api.order.application.port.in.GetOrdersAppResponse;
import com.latinhouse.api.order.application.port.in.GetOrdersUseCase;
import com.latinhouse.api.order.application.port.out.LoadOrderPort;
import com.latinhouse.api.order.domain.Order;
import com.latinhouse.api.order.domain.OrderDiscount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetOrdersService implements GetOrdersUseCase {

    private final LoadOrderPort loadOrderPort;

    @Override
    @Transactional(readOnly = true)
    public List<GetOrdersAppResponse> getOrders(GetOrdersAppRequest request) {
        List<Order> orders = loadOrderPort.loadOrders(request.getBuyer(), request.getLessonNo());
        return orders.stream()
                .map(this::toAppResponse)
                .toList();
    }

    private GetOrdersAppResponse toAppResponse(Order order) {
        List<GetOrdersAppResponse.DiscountInfo> discounts = order.getDiscounts() == null ? List.of() :
                order.getDiscounts().stream()
                        .map(this::toDiscountInfo)
                        .toList();

        return GetOrdersAppResponse.builder()
                .orderId(order.getId())
                .lessonNo(order.getLessonNo())
                .lessonOptionNo(order.getLessonOptionNo())
                .price(order.getPrice())
                .status(order.getStatus().name())
                .discounts(discounts)
                .build();
    }

    private GetOrdersAppResponse.DiscountInfo toDiscountInfo(OrderDiscount discount) {
        return GetOrdersAppResponse.DiscountInfo.builder()
                .discountType(discount.getDiscountType().name())
                .discountId(discount.getDiscountId())
                .amount(discount.getAmount())
                .build();
    }
}
