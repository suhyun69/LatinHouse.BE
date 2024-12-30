package com.latinhouse.backend.lesson.port.in;

import com.latinhouse.backend.lesson.port.in.response.GetLessonAppResponse;

import java.util.List;

public interface GetLessonUseCase {
    GetLessonAppResponse getLessonById(Long id);
    List<GetLessonAppResponse> getAllLessons();
}