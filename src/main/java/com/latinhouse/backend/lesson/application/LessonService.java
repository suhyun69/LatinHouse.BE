package com.latinhouse.backend.lesson.application;

import com.latinhouse.backend.lesson.domain.Lesson;
import com.latinhouse.backend.lesson.port.in.CreateLessonUseCase;
import com.latinhouse.backend.lesson.port.in.GetLessonUseCase;
import com.latinhouse.backend.lesson.port.in.request.CreateLessonAppRequest;
import com.latinhouse.backend.lesson.port.in.response.CreateLessonAppResponse;
import com.latinhouse.backend.lesson.port.in.response.GetLessonAppResponse;
import com.latinhouse.backend.lesson.port.out.CreateLessonPort;
import com.latinhouse.backend.lesson.port.out.GetLessonPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService implements CreateLessonUseCase, GetLessonUseCase {

    private final CreateLessonPort createLessonPort;
    private final GetLessonPort getLessonPort;

    @Override
    public CreateLessonAppResponse createLesson(CreateLessonAppRequest appReq) {
        Lesson lesson = createLessonPort.createLesson(appReq);
        return new CreateLessonAppResponse(lesson);
    }

    @Override
    public GetLessonAppResponse getLessonById(Long id) {
        Lesson lesson = getLessonPort.getLessonById(id);
        return new GetLessonAppResponse(lesson);
    }

    @Override
    public List<GetLessonAppResponse> getAllLessons() {
        List<Lesson> lessons = getLessonPort.getAllLessons();
        return lessons.stream()
                .map(GetLessonAppResponse::new)
                .collect(Collectors.toList());
    }
}