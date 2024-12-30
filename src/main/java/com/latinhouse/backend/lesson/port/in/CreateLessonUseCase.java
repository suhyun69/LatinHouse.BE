package com.latinhouse.backend.lesson.port.in;

import com.latinhouse.backend.lesson.port.in.request.CreateLessonAppRequest;
import com.latinhouse.backend.lesson.port.in.response.CreateLessonAppResponse;
import com.latinhouse.backend.profile.port.in.response.CreateProfileAppResponse;

public interface CreateLessonUseCase {
    CreateLessonAppResponse createLesson(CreateLessonAppRequest request);
}