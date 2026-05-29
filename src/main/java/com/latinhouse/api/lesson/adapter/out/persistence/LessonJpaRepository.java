package com.latinhouse.api.lesson.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface LessonJpaRepository extends JpaRepository<LessonEntity, Long> {
}
