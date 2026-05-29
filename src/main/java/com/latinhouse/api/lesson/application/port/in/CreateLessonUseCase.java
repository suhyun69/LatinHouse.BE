package com.latinhouse.api.lesson.application.port.in;

public interface CreateLessonUseCase {
    CreateLessonAppResponse createLesson(CreateLessonAppRequest request);
}
