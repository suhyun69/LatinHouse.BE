package com.latinhouse.api.lesson.application.service;

import com.latinhouse.api.lesson.application.port.in.GetLessonAppMapper;
import com.latinhouse.api.lesson.application.port.in.GetLessonAppResponse;
import com.latinhouse.api.lesson.application.port.in.GetLessonUseCase;
import com.latinhouse.api.lesson.application.port.out.LoadLessonPort;
import com.latinhouse.api.lesson.domain.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetLessonService implements GetLessonUseCase {

    private final LoadLessonPort loadLessonPort;

    @Override
    public GetLessonAppResponse getLesson(Long lessonNo) {
        Lesson lesson = loadLessonPort.loadLesson(lessonNo);
        return GetLessonAppMapper.toAppResponse(lesson);
    }
}
