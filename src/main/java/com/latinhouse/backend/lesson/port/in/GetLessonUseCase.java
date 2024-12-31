package com.latinhouse.backend.lesson.port.in;

import com.latinhouse.backend.lesson.port.in.response.GetLessonAppResponse;
import com.latinhouse.backend.lesson.port.in.response.LessonDetailInfo;

import java.util.List;

public interface GetLessonUseCase {
    GetLessonAppResponse getLessonByNo(Long no);
    List<GetLessonAppResponse> getAllLessons();
}