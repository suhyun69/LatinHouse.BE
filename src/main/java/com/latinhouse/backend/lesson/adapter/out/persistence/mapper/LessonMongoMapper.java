package com.latinhouse.backend.lesson.adapter.out.persistence.mapper;

import com.latinhouse.backend.lesson.adapter.out.persistence.entity.*;
import com.latinhouse.backend.lesson.domain.*;
import com.latinhouse.backend.lesson.port.in.request.CreateLessonAppRequest;
import com.latinhouse.backend.profile.domain.Profile;
import com.latinhouse.backend.profile.domain.Sex;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LessonMongoMapper {

    public LessonMongoEntity mapToMongoEntity(CreateLessonAppRequest req) {
        return LessonMongoEntity.builder()
                .title(req.getTitle())
                .build();
    }

    public Lesson mapToDomainEntity(LessonMongoEntity lessonT) {
        return Lesson.builder()
                .no(lessonT.getNo())
                .title(lessonT.getTitle())
                .build();
    }
}