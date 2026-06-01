package com.latinhouse.api.lesson.application.port.in;

public interface GetLessonUseCase {
    GetLessonAppResponse getLesson(Long lessonNo);
}
