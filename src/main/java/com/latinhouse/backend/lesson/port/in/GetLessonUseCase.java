package com.latinhouse.backend.lesson.port.in;

import com.latinhouse.backend.lesson.port.in.response.GetLessonAppResponse;
import com.latinhouse.backend.lesson.port.in.response.LessonInfo;

import java.util.List;

public interface GetLessonUseCase {
    LessonInfo getLessonByNo(Long no);
}