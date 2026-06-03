package com.latinhouse.api.order.adapter.in.web;

import com.latinhouse.api.order.application.port.in.CreateOrderUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order", description = "주문 관리 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

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
}
