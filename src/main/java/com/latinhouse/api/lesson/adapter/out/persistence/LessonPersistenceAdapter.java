package com.latinhouse.api.lesson.adapter.out.persistence;

import com.latinhouse.api.lesson.application.port.out.SaveLessonPort;
import com.latinhouse.api.lesson.domain.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class LessonPersistenceAdapter implements SaveLessonPort {

    private final LessonJpaRepository lessonJpaRepository;

    @Override
    public Lesson save(Lesson lesson) {
        LessonEntity entity = LessonPersistenceMapper.toEntity(lesson);
        LessonEntity saved = lessonJpaRepository.save(entity);
        return LessonPersistenceMapper.toDomain(saved);
    }
}
