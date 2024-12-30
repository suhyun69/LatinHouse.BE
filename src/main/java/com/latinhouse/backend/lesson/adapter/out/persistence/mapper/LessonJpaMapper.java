package com.latinhouse.backend.lesson.adapter.out.persistence.mapper;

import com.latinhouse.backend.lesson.adapter.out.persistence.entity.LessonJpaEntity;
import com.latinhouse.backend.lesson.domain.Lesson;
import com.latinhouse.backend.lesson.port.in.request.CreateLessonAppRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LessonJpaMapper {

    public LessonJpaEntity mapToJpaEntity(CreateLessonAppRequest appReq) {
        return LessonJpaEntity.builder()
                .title(appReq.getTitle())
                .build();
    }

    public Lesson mapToDomainEntity(LessonJpaEntity lessonT) {
        return Lesson.builder()
                .no(lessonT.getNo())
                .title(lessonT.getTitle())
                .build();
    }
}
