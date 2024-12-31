package com.latinhouse.backend.lesson.port.in.response;

import com.latinhouse.backend.lesson.domain.Lesson;
import com.latinhouse.backend.profile.domain.Profile;
import lombok.Getter;

@Getter
public class LessonSummaryInfo extends GetLessonAppResponse{
    public LessonSummaryInfo(Lesson l, Profile instructor1, Profile instructor2)
    {
        super(l, instructor1, instructor2);
    }
}
