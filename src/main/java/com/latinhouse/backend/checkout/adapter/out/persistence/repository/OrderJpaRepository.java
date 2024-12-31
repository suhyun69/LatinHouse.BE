package com.latinhouse.backend.checkout.adapter.out.persistence.repository;

import com.latinhouse.backend.checkout.adapter.out.persistence.entity.OrderJpaEntity;
import com.latinhouse.backend.lesson.adapter.out.persistence.entity.LessonJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, Long> {
}
