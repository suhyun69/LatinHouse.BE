package com.latinhouse.backend.checkout.port.in.response;

import com.latinhouse.backend.checkout.domain.Order;
import com.latinhouse.backend.profile.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // test
public class EnrollLessonAppResponse {
    private Long orderSeq;
    private String status;

    public EnrollLessonAppResponse(Order order) {
        this.orderSeq = order.getSeq();
    }
}
