package com.latinhouse.api.order.adapter.in.web;

import com.latinhouse.api.order.adapter.in.web.request.CreateOrderWebRequest;
import com.latinhouse.api.order.adapter.in.web.response.OrderWebResponse;
import com.latinhouse.api.order.adapter.in.web.response.PagedOrderWebResponse;
import com.latinhouse.api.order.port.in.CreateOrderUseCase;
import com.latinhouse.api.order.port.in.FindOrderUseCase;
import com.latinhouse.api.order.port.in.request.CreateOrderAppRequest;
import com.latinhouse.api.order.port.in.request.FindOrderAppRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Order", description = "Order API")
@RequiredArgsConstructor
public class ApiV1OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final FindOrderUseCase findOrderUseCase;

    @PostMapping
    @Operation(summary = "Create order")
    public ResponseEntity<OrderWebResponse> create(@Valid @RequestBody CreateOrderWebRequest webReq) {
        CreateOrderAppRequest appReq = CreateOrderAppRequest.from(webReq);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new OrderWebResponse(createOrderUseCase.create(appReq)));
    }

    @GetMapping("/{no}")
    @Operation(summary = "Find order by no")
    public ResponseEntity<OrderWebResponse> findByNo(@PathVariable("no") Long no) {
        return ResponseEntity.ok(new OrderWebResponse(findOrderUseCase.findByNo(no)));
    }

    @GetMapping
    @Operation(summary = "Find all orders (paginated)")
    public ResponseEntity<PagedOrderWebResponse> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "profileId", required = false) String profileId,
            @RequestParam(value = "lessonNo", required = false) Long lessonNo,
            @RequestParam(value = "instructorLo", required = false) String instructorLo,
            @RequestParam(value = "instructorLa", required = false) String instructorLa) {
        FindOrderAppRequest searchReq = FindOrderAppRequest.of(profileId, lessonNo, instructorLo, instructorLa);
        return ResponseEntity.ok(new PagedOrderWebResponse(findOrderUseCase.findAll(page, size, searchReq)));
    }
}
