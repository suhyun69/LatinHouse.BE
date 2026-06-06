package com.latinhouse.api.order.adapter.out.persistence;

import com.latinhouse.api.common.exception.LessonOptionNotFoundException;
import com.latinhouse.api.lesson.adapter.out.persistence.LessonOptionJpaRepository;
import com.latinhouse.api.order.application.port.out.LoadLessonOptionPort;
import com.latinhouse.api.order.application.port.out.LoadOrderPort;
import com.latinhouse.api.order.application.port.out.SaveOrderPort;
import com.latinhouse.api.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
class OrderPersistenceAdapter implements SaveOrderPort, LoadLessonOptionPort, LoadOrderPort {

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

    @Override
    public List<Order> loadOrders(String buyer, Long lessonNo) {
        Specification<OrderEntity> spec = buildSpec(buyer, lessonNo);
        return orderJpaRepository.findAll(spec).stream()
                .map(OrderPersistenceMapper::toDomain)
                .toList();
    }

    private Specification<OrderEntity> buildSpec(String buyer, Long lessonNo) {
        Specification<OrderEntity> spec = (root, query, cb) -> cb.conjunction();
        if (buyer != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("buyer"), buyer));
        }
        if (lessonNo != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("lessonNo"), lessonNo));
        }
        return spec;
    }
}
