package com.latinhouse.api.order.port.out;

import com.latinhouse.api.order.domain.Order;
import com.latinhouse.api.order.port.in.request.FindOrderAppRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReadOrderPort {
    Optional<Order> findByNo(Long no);
    Page<Order> findAll(Pageable pageable, FindOrderAppRequest searchReq);
}
