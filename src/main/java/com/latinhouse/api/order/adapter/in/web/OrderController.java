package com.latinhouse.api.order.adapter.in.web;

import com.latinhouse.api.order.application.port.in.CreateOrderUseCase;
import com.latinhouse.api.order.application.port.in.GetOrdersUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Order", description = "주문 관리 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrdersUseCase getOrdersUseCase;

    @Operation(summary = "주문 생성", description = "레슨 주문을 생성합니다.")
    @PostMapping("/order")
    public ResponseEntity<CreateOrderWebResponse> createOrder(
            @Valid @RequestBody CreateOrderWebRequest request) {
        CreateOrderWebResponse response = CreateOrderWebMapper.toWebResponse(
                createOrderUseCase.createOrder(
                        CreateOrderWebMapper.toAppRequest(request)
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "주문 목록 조회", description = "buyer 또는 lessonNo 조건으로 주문 목록을 조회합니다.")
    @GetMapping("/orders")
    public ResponseEntity<List<GetOrdersWebResponse>> getOrders(
            @RequestParam(required = false) String buyer,
            @RequestParam(required = false) String lessonNo) {
        List<GetOrdersWebResponse> response = GetOrdersWebMapper.toWebResponseList(
                getOrdersUseCase.getOrders(
                        GetOrdersWebMapper.toAppRequest(buyer, lessonNo)
                )
        );
        return ResponseEntity.ok(response);
    }
}
