package com.latinhouse.backend.lesson.adapter.out.persistence;

import com.latinhouse.backend.lesson.adapter.out.persistence.entity.LessonJpaEntity;
import com.latinhouse.backend.lesson.adapter.out.persistence.entity.LessonMongoEntity;
import com.latinhouse.backend.lesson.adapter.out.persistence.mapper.LessonJpaMapper;
import com.latinhouse.backend.lesson.adapter.out.persistence.mapper.LessonMongoMapper;
import com.latinhouse.backend.lesson.adapter.out.persistence.repository.LessonJpaRepository;
import com.latinhouse.backend.lesson.adapter.out.persistence.repository.LessonMongoRepository;
import com.latinhouse.backend.lesson.adapter.out.persistence.service.LessonSequenceService;
import com.latinhouse.backend.lesson.domain.Lesson;
import com.latinhouse.backend.lesson.port.in.request.CreateLessonAppRequest;
import com.latinhouse.backend.lesson.port.out.CreateLessonPort;
import com.latinhouse.backend.lesson.port.out.GetLessonPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LessonPersistenceAdapter implements
        CreateLessonPort,
        GetLessonPort
{
    private final LessonJpaMapper lessonJpaMapper;
    private final LessonJpaRepository lessonJpaRepository;

    private final LessonMongoMapper lessonMongoMapper;
    private final LessonSequenceService lessonSequenceService;
    private final LessonMongoRepository lessonMongoRepository;

    @Override
    public Lesson createLesson(CreateLessonAppRequest appReq) {

        LessonMongoEntity lessonT = lessonMongoMapper.mapToMongoEntity(appReq);

        long nextSequence = lessonSequenceService.getNextSequence("lessons_sequence");
        lessonT.setNo(nextSequence);

        lessonT = lessonMongoRepository.save(lessonT);

        return lessonMongoMapper.mapToDomainEntity(lessonT);

        // LessonJpaEntity lessonT = lessonJpaRepository.save(lessonJpaMapper.mapToJpaEntity(appReq));
        // return lessonJpaMapper.mapToDomainEntity(lessonT);
    }

    @Override
    public Lesson getLessonByNo(Long no) {

        LessonMongoEntity lessonT = lessonMongoRepository.findByNo(no)
                .orElseThrow(() -> new IllegalArgumentException("Lesson no " + no + " not found"));

        return lessonMongoMapper.mapToDomainEntity(lessonT);

        /*
        LessonJpaEntity lessonT = lessonJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lesson ID " + id + " not found"));
        return lessonJpaMapper.mapToDomainEntity(lessonT);
        */
    }

    @Override
    public List<Lesson> getAllLessons() {
        List<LessonJpaEntity> lessonTList = lessonJpaRepository.findAll();
        return lessonTList.stream().map(lessonJpaMapper::mapToDomainEntity).toList();
    }
}
