package com.latinhouse.backend.lesson.adapter.out.persistence.repository;

import com.latinhouse.backend.lesson.adapter.out.persistence.entity.LessonMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LessonMongoRepository extends MongoRepository<LessonMongoEntity, String> {
    Optional<LessonMongoEntity> findByNo(Long no);
}
