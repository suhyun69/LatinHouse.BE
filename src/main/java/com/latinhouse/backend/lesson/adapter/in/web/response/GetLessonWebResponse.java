package com.latinhouse.backend.lesson.adapter.in.web.response;

import com.latinhouse.backend.lesson.port.in.response.LessonInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetLessonWebResponse {
    private Long no;
    private String title;

    public GetLessonWebResponse(LessonInfo appRes) {
        this.no = appRes.getLessonNo();
        this.title = appRes.getTitle();
    }
}
