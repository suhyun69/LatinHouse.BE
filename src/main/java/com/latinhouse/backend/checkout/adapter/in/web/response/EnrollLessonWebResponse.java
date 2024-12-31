package com.latinhouse.backend.checkout.adapter.in.web.response;

import com.latinhouse.backend.checkout.port.in.response.EnrollLessonAppResponse;
import com.latinhouse.backend.profile.port.in.response.CreateProfileAppResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnrollLessonWebResponse {
    private Long orderSeq;
    private String status;

    public EnrollLessonWebResponse(EnrollLessonAppResponse appRes) {
        this.orderSeq = appRes.getOrderSeq();
        this.status = "OK";
    }
}
