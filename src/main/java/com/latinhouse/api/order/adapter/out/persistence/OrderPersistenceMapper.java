package com.latinhouse.api.order.adapter.out.persistence;

import com.latinhouse.api.order.domain.Order;
import com.latinhouse.api.order.domain.OrderDiscount;
import com.latinhouse.api.order.domain.OrderDiscountType;
import com.latinhouse.api.order.domain.OrderStatus;

import java.util.List;

class OrderPersistenceMapper {

    private OrderPersistenceMapper() {}

    static OrderEntity toEntity(Order order) {
        List<OrderDiscountEntity> discountEntities = order.getDiscounts() == null ? List.of() :
                order.getDiscounts().stream()
                        .map(d -> OrderDiscountEntity.builder()
                                .id(d.getId())
                                .orderId(order.getId())
                                .discountType(d.getDiscountType().name())
                                .discountId(d.getDiscountId())
                                .amount(d.getAmount())
                                .build())
                        .toList();

        return OrderEntity.builder()
                .id(order.getId())
                .lessonNo(order.getLessonNo())
                .lessonOptionNo(order.getLessonOptionNo())
                .buyer(order.getBuyer())
                .price(order.getPrice())
                .paymentId(order.getPaymentId())
                .status(order.getStatus().name())
                .discounts(discountEntities)
                .build();
    }

    static Order toDomain(OrderEntity entity) {
        List<OrderDiscount> discounts = entity.getDiscounts() == null ? List.of() :
                entity.getDiscounts().stream()
                        .map(d -> OrderDiscount.builder()
                                .id(d.getId())
                                .orderId(d.getOrderId())
                                .discountType(OrderDiscountType.valueOf(d.getDiscountType()))
                                .discountId(d.getDiscountId())
                                .amount(d.getAmount())
                                .build())
                        .toList();

        return Order.builder()
                .id(entity.getId())
                .lessonNo(entity.getLessonNo())
                .lessonOptionNo(entity.getLessonOptionNo())
                .buyer(entity.getBuyer())
                .price(entity.getPrice())
                .paymentId(entity.getPaymentId())
                .status(OrderStatus.valueOf(entity.getStatus()))
                .discounts(discounts)
                .build();
    }
}
