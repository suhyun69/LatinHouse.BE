package com.latinhouse.backend.lesson.application;

import com.latinhouse.backend.lesson.domain.Lesson;
import com.latinhouse.backend.lesson.port.in.CreateLessonUseCase;
import com.latinhouse.backend.lesson.port.in.GetLessonUseCase;
import com.latinhouse.backend.lesson.port.in.request.CreateLessonAppRequest;
import com.latinhouse.backend.lesson.port.in.response.CreateLessonAppResponse;
import com.latinhouse.backend.lesson.port.in.response.LessonInfo;
import com.latinhouse.backend.lesson.port.out.CreateLessonPort;
import com.latinhouse.backend.lesson.port.out.GetLessonPort;
import com.latinhouse.backend.profile.domain.Profile;
import com.latinhouse.backend.profile.port.out.GetProfilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonService implements
        CreateLessonUseCase,
        GetLessonUseCase
{

    private final CreateLessonPort createLessonPort;
    private final GetLessonPort getLessonPort;

    private final GetProfilePort getProfilePort;

    @Override
    public CreateLessonAppResponse createLesson(CreateLessonAppRequest appReq) {
        Lesson lesson = createLessonPort.createLesson(appReq);
        return new CreateLessonAppResponse(lesson);
    }

    @Override
    public LessonInfo getLessonByNo(Long no) {
        Lesson lesson = getLessonPort.getLessonByNo(no);

        Profile instructor1 = getProfilePort.getProfileById(lesson.getInstructor1());
        Profile instructor2 = null;
        if(Optional.ofNullable(lesson.getInstructor2()).isPresent()) {
            instructor2 = getProfilePort.getProfileById(lesson.getInstructor2());
        }

        return new LessonInfo(lesson, instructor1, instructor2);
    }
}