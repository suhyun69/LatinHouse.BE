package com.latinhouse.backend.lesson.adapter.out.persistence.repository;

import com.latinhouse.backend.lesson.adapter.out.persistence.entity.LessonSequence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LessonSequenceMongoRepository extends MongoRepository<LessonSequence, String> {
}