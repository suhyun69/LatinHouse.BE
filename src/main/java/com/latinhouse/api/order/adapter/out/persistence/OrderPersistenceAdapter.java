package com.latinhouse.api.order.adapter.out.persistence;

import com.latinhouse.api.common.exception.LessonOptionNotFoundException;
import com.latinhouse.api.lesson.adapter.out.persistence.LessonOptionJpaRepository;
import com.latinhouse.api.order.application.port.out.LoadLessonOptionPort;
import com.latinhouse.api.order.application.port.out.SaveOrderPort;
import com.latinhouse.api.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class OrderPersistenceAdapter implements SaveOrderPort, LoadLessonOptionPort {

    private final OrderJpaRepository orderJpaRepository;
    private final LessonOptionJpaRepository lessonOptionJpaRepository;

    @Override
    public Order save(Order order) {
        OrderEntity entity = OrderPersistenceMapper.toEntity(order);
        OrderEntity saved = orderJpaRepository.save(entity);
        return OrderPersistenceMapper.toDomain(saved);
    }

    @Override
    public void load(Long lessonOptionNo) {
        lessonOptionJpaRepository.findById(lessonOptionNo)
                .orElseThrow(() -> new LessonOptionNotFoundException(lessonOptionNo));
    }
}
