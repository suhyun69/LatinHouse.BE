package com.latinhouse.backend.lesson.application;

import com.latinhouse.backend.lesson.domain.Lesson;
import com.latinhouse.backend.lesson.port.in.CreateLessonUseCase;
import com.latinhouse.backend.lesson.port.in.GetLessonUseCase;
import com.latinhouse.backend.lesson.port.in.request.CreateLessonAppRequest;
import com.latinhouse.backend.lesson.port.in.response.CreateLessonAppResponse;
import com.latinhouse.backend.lesson.port.in.response.GetLessonAppResponse;
import com.latinhouse.backend.lesson.port.in.response.LessonDetailInfo;
import com.latinhouse.backend.lesson.port.in.response.LessonSummaryInfo;
import com.latinhouse.backend.lesson.port.out.CreateLessonPort;
import com.latinhouse.backend.lesson.port.out.GetLessonPort;
import com.latinhouse.backend.profile.domain.Profile;
import com.latinhouse.backend.profile.port.out.GetProfilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

        Profile instructor1 = getProfilePort.getProfileById(appReq.getInstructor1());
        if(!instructor1.getIsInstructor()) {
            throw new IllegalArgumentException("Profile ID " + appReq.getInstructor1() + " is not instructor");
        }
        Profile instructor2 = null;
        if(Optional.ofNullable(appReq.getInstructor2()).isPresent()) {
            instructor2 = getProfilePort.getProfileById(appReq.getInstructor2());
            if(!instructor2.getIsInstructor()) {
                throw new IllegalArgumentException("Profile ID " + appReq.getInstructor2() + " is not instructor");
            }
        }

        Lesson lesson = createLessonPort.createLesson(appReq);
        return new CreateLessonAppResponse(lesson);
    }

    @Override
    public GetLessonAppResponse getLessonByNo(Long no) {
        Lesson lesson = getLessonPort.getLessonByNo(no);

        Profile instructor1 = getProfilePort.getProfileById(lesson.getInstructor1());
        Profile instructor2 = null;
        if(Optional.ofNullable(lesson.getInstructor2()).isPresent()) {
            instructor2 = getProfilePort.getProfileById(lesson.getInstructor2());
        }

        return new LessonDetailInfo(lesson, instructor1, instructor2);
    }

    @Override
    public List<GetLessonAppResponse> getAllLessons() {

        List<GetLessonAppResponse> appResList = new ArrayList<>();

        List<Lesson> lessonList = getLessonPort.getAllLessons();
        for(Lesson lesson: lessonList) {
            Profile instructor1 = getProfilePort.getProfileById(lesson.getInstructor1());
            Profile instructor2 = null;
            if(Optional.ofNullable(lesson.getInstructor2()).isPresent()) {
                instructor2 = getProfilePort.getProfileById(lesson.getInstructor2());
            }
            appResList.add(new LessonSummaryInfo(lesson, instructor1, instructor2));
        }

        return appResList;
    }
}