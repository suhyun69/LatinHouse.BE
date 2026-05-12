package com.latinhouse.api.order.port.in;

import com.latinhouse.api.order.port.in.request.FindOrderAppRequest;
import com.latinhouse.api.order.port.in.response.OrderAppResponse;
import com.latinhouse.api.order.port.in.response.PagedOrderAppResponse;

public interface FindOrderUseCase {
    OrderAppResponse findByNo(Long no);
    PagedOrderAppResponse findAll(int page, int size, FindOrderAppRequest searchReq);
}
