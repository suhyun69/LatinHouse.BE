package com.latinhouse.backend.lesson.port.out;

import com.latinhouse.backend.lesson.domain.Lesson;
import com.latinhouse.backend.lesson.port.in.request.CreateLessonAppRequest;
import com.latinhouse.backend.lesson.port.in.response.CreateLessonAppResponse;

public interface CreateLessonPort {
    Lesson createLesson(CreateLessonAppRequest appReq);
}
