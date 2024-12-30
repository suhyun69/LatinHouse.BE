package com.latinhouse.backend.lesson.domain;

import com.latinhouse.backend.lesson.port.in.request.CreateLessonAppRequest;
import com.latinhouse.backend.profile.domain.Sex;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
// @AllArgsConstructor
public class Lesson {

    private Long no;
    private String title;

    public Lesson(Long no, String title) {
        this.no = no;
        this.title = title;
    }
}