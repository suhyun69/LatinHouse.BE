package com.latinhouse.backend.lesson.port.in.request;

import com.latinhouse.backend.lesson.adapter.in.web.request.CreateLessonWebRequest;
import com.latinhouse.backend.lesson.domain.Lesson;
import com.latinhouse.backend.profile.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // test
public class CreateLessonAppRequest {
    private String title;

    public CreateLessonAppRequest(CreateLessonWebRequest webReq) {
        this.title = webReq.getTitle();
    }
}
