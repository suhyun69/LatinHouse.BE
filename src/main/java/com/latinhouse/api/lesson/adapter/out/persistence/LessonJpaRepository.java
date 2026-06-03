package com.latinhouse.api.lesson.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface LessonJpaRepository extends JpaRepository<LessonEntity, Long>, JpaSpecificationExecutor<LessonEntity> {
}
