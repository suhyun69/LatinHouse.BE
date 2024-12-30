package com.latinhouse.backend.lesson.port.in.response;

import com.latinhouse.backend.lesson.domain.Lesson;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // test
public class CreateLessonAppResponse {
    private Long no;
    private String title;

    public CreateLessonAppResponse(Lesson lesson) {
        this.no = lesson.getNo();
        this.title = lesson.getTitle();
    }
}
