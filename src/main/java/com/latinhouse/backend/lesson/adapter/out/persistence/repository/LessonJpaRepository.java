package com.latinhouse.backend.lesson.adapter.out.persistence.repository;

import com.latinhouse.backend.lesson.adapter.out.persistence.entity.LessonJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonJpaRepository extends JpaRepository<LessonJpaEntity, Long> {
}
