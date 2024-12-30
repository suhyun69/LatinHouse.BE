package com.latinhouse.backend.lesson.adapter.in.web.response;

import com.latinhouse.backend.lesson.port.in.response.GetLessonAppResponse;
import com.latinhouse.backend.profile.port.in.response.GetProfileAppResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetLessonWebResponse {
    private Long no;
    private String title;

    public GetLessonWebResponse(GetLessonAppResponse appRes) {
        this.no = appRes.getNo();
        this.title = appRes.getTitle();
    }
}
