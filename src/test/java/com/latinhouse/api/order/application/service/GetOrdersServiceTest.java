package com.latinhouse.api.order.application.service;

import com.latinhouse.api.order.application.port.in.GetOrdersAppRequest;
import com.latinhouse.api.order.application.port.in.GetOrdersAppResponse;
import com.latinhouse.api.order.application.port.out.LoadOrderPort;
import com.latinhouse.api.order.domain.Order;
import com.latinhouse.api.order.domain.OrderDiscount;
import com.latinhouse.api.order.domain.OrderDiscountType;
import com.latinhouse.api.order.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetOrdersServiceTest {

    @InjectMocks
    private GetOrdersService getOrdersService;

    @Mock
    private LoadOrderPort loadOrderPort;

    private Order sampleOrder(String orderId, String buyer, Long lessonNo) {
        return Order.builder()
                .id(orderId)
                .lessonNo(lessonNo)
                .lessonOptionNo(3L)
                .buyer(buyer)
                .price(BigDecimal.valueOf(80000))
                .discounts(List.of(
                        OrderDiscount.builder()
                                .id(1L)
                                .orderId(orderId)
                                .discountType(OrderDiscountType.LESSON)
                                .discountId(10L)
                                .amount(BigDecimal.valueOf(5000))
                                .build()
                ))
                .status(OrderStatus.PAYMENT_PENDING)
                .build();
    }

    @Test
    void getOrders_byBuyer_returnsOnlyBuyerOrders() {
        Order order1 = sampleOrder("uuid-1", "Ab2Cd3Ef", 1L);
        Order order2 = sampleOrder("uuid-2", "Ab2Cd3Ef", 2L);
        when(loadOrderPort.loadOrders("Ab2Cd3Ef", null)).thenReturn(List.of(order1, order2));

        List<GetOrdersAppResponse> result = getOrdersService.getOrders(
                GetOrdersAppRequest.builder().buyer("Ab2Cd3Ef").lessonNo(null).build()
        );

        assertThat(result).hasSize(2);
        assertThat(result).allMatch(r -> r.getOrderId().startsWith("uuid-"));
        assertThat(result.get(0).getDiscounts()).hasSize(1);
        assertThat(result.get(0).getDiscounts().get(0).getDiscountType()).isEqualTo("LESSON");
        verify(loadOrderPort).loadOrders("Ab2Cd3Ef", null);
    }

    @Test
    void getOrders_byLessonNo_returnsOnlyLessonOrders() {
        Order order = sampleOrder("uuid-1", "Ab2Cd3Ef", 1L);
        when(loadOrderPort.loadOrders(null, 1L)).thenReturn(List.of(order));

        List<GetOrdersAppResponse> result = getOrdersService.getOrders(
                GetOrdersAppRequest.builder().buyer(null).lessonNo(1L).build()
        );

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getLessonNo()).isEqualTo(1L);
        verify(loadOrderPort).loadOrders(null, 1L);
    }

    @Test
    void getOrders_byBuyerAndLessonNo_andCondition() {
        Order order = sampleOrder("uuid-1", "Ab2Cd3Ef", 1L);
        when(loadOrderPort.loadOrders("Ab2Cd3Ef", 1L)).thenReturn(List.of(order));

        List<GetOrdersAppResponse> result = getOrdersService.getOrders(
                GetOrdersAppRequest.builder().buyer("Ab2Cd3Ef").lessonNo(1L).build()
        );

        assertThat(result).hasSize(1);
        verify(loadOrderPort).loadOrders("Ab2Cd3Ef", 1L);
    }

    @Test
    void getOrders_noMatch_returnsEmptyList() {
        when(loadOrderPort.loadOrders("unknown", null)).thenReturn(List.of());

        List<GetOrdersAppResponse> result = getOrdersService.getOrders(
                GetOrdersAppRequest.builder().buyer("unknown").lessonNo(null).build()
        );

        assertThat(result).isEmpty();
    }

    @Test
    void getOrders_noParams_returnsAll() {
        Order order1 = sampleOrder("uuid-1", "Ab2Cd3Ef", 1L);
        Order order2 = sampleOrder("uuid-2", "Xy9Zw8Vt", 2L);
        when(loadOrderPort.loadOrders(null, null)).thenReturn(List.of(order1, order2));

        List<GetOrdersAppResponse> result = getOrdersService.getOrders(
                GetOrdersAppRequest.builder().buyer(null).lessonNo(null).build()
        );

        assertThat(result).hasSize(2);
        verify(loadOrderPort).loadOrders(null, null);
    }
}
