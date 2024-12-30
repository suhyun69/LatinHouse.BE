package com.latinhouse.backend.lesson.adapter.in.web.response;

import com.latinhouse.backend.lesson.port.in.response.CreateLessonAppResponse;
import com.latinhouse.backend.profile.port.in.response.CreateProfileAppResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateLessonWebResponse {

    private Long no;
    private String title;

    public CreateLessonWebResponse(CreateLessonAppResponse appRes) {
        this.no = appRes.getNo();
        this.title = appRes.getTitle();
    }
}
