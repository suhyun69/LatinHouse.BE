package com.latinhouse.api.lesson.adapter.out.persistence;

import com.latinhouse.api.common.exception.LessonNotFoundException;
import com.latinhouse.api.lesson.application.port.out.LoadLessonPort;
import com.latinhouse.api.lesson.application.port.out.SaveLessonPort;
import com.latinhouse.api.lesson.domain.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class LessonPersistenceAdapter implements SaveLessonPort, LoadLessonPort {

    private final LessonJpaRepository lessonJpaRepository;

    @Override
    public Lesson save(Lesson lesson) {
        LessonEntity entity = LessonPersistenceMapper.toEntity(lesson);
        LessonEntity saved = lessonJpaRepository.save(entity);
        return LessonPersistenceMapper.toDomain(saved);
    }

    @Override
    public Lesson loadLesson(Long lessonNo) {
        LessonEntity entity = lessonJpaRepository.findById(lessonNo)
                .orElseThrow(() -> new LessonNotFoundException(lessonNo));
        return LessonPersistenceMapper.toDomain(entity);
    }
}
