package com.latinhouse.api.order.adapter.in.web.response;

import com.latinhouse.api.order.port.in.response.PagedOrderAppResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class PagedOrderWebResponse {

    private final List<OrderWebResponse> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    private final boolean first;
    private final boolean last;

    public PagedOrderWebResponse(PagedOrderAppResponse appResponse) {
        this.content = appResponse.getContent().stream()
                .map(OrderWebResponse::new)
                .toList();
        this.page = appResponse.getPage();
        this.size = appResponse.getSize();
        this.totalElements = appResponse.getTotalElements();
        this.totalPages = appResponse.getTotalPages();
        this.first = appResponse.isFirst();
        this.last = appResponse.isLast();
    }
}
