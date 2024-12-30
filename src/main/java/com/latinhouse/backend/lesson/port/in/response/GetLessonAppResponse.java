package com.latinhouse.backend.lesson.port.in.response;

import com.latinhouse.backend.lesson.domain.Lesson;
import com.latinhouse.backend.profile.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // test
public class GetLessonAppResponse {
    private Long no;
    private String title;

    public GetLessonAppResponse(Lesson lesson) {
        this.no = lesson.getNo();
        this.title = lesson.getTitle();
    }
}
