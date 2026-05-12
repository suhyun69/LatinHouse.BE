package com.latinhouse.api.order.adapter.out.persistence;

import com.latinhouse.api.lesson.adapter.out.persistence.entity.LessonJpaEntity;
import com.latinhouse.api.order.adapter.out.persistence.entity.OrderJpaEntity;
import com.latinhouse.api.order.adapter.out.persistence.mapper.OrderMapper;
import com.latinhouse.api.order.adapter.out.persistence.repository.OrderRepository;
import com.latinhouse.api.order.domain.Order;
import com.latinhouse.api.order.port.in.request.FindOrderAppRequest;
import com.latinhouse.api.order.port.out.CreateOrderPort;
import com.latinhouse.api.order.port.out.ReadOrderPort;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements CreateOrderPort, ReadOrderPort {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @Override
    public Order create(Order order) {
        return orderMapper.mapToDomainEntity(
                orderRepository.save(orderMapper.mapToJpaEntity(order)));
    }

    @Override
    public Optional<Order> findByNo(Long no) {
        return orderRepository.findById(no)
                .map(orderMapper::mapToDomainEntity);
    }

    @Override
    public Page<Order> findAll(Pageable pageable, FindOrderAppRequest searchReq) {
        Specification<OrderJpaEntity> spec = buildSpecification(searchReq);
        return orderRepository.findAll(spec, pageable)
                .map(orderMapper::mapToDomainEntity);
    }

    /**
     * FindOrderAppRequest의 조건을 JPA Specification으로 변환한다.
     * null 필드는 조건에서 제외한다.
     *
     * profileId  : 정확히 일치
     * lessonNo   : 정확히 일치
     * instructorLo/instructorLa: Order → Lesson JOIN 후 LIKE lower() 검색
     */
    private Specification<OrderJpaEntity> buildSpecification(FindOrderAppRequest searchReq) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            query.distinct(true);

            if (searchReq.getProfileId() != null && !searchReq.getProfileId().isBlank()) {
                predicates.add(cb.equal(root.get("profileId"), searchReq.getProfileId()));
            }

            if (searchReq.getLessonNo() != null) {
                predicates.add(cb.equal(root.get("lessonNo"), searchReq.getLessonNo()));
            }

            boolean needsLessonJoin = (searchReq.getInstructorLo() != null && !searchReq.getInstructorLo().isBlank())
                    || (searchReq.getInstructorLa() != null && !searchReq.getInstructorLa().isBlank());

            if (needsLessonJoin) {
                Join<OrderJpaEntity, LessonJpaEntity> lessonJoin =
                        root.join("lesson", JoinType.LEFT);

                if (searchReq.getInstructorLo() != null && !searchReq.getInstructorLo().isBlank()) {
                    String pattern = "%" + searchReq.getInstructorLo().toLowerCase() + "%";
                    predicates.add(cb.like(cb.lower(lessonJoin.get("instructorLo")), pattern));
                }

                if (searchReq.getInstructorLa() != null && !searchReq.getInstructorLa().isBlank()) {
                    String pattern = "%" + searchReq.getInstructorLa().toLowerCase() + "%";
                    predicates.add(cb.like(cb.lower(lessonJoin.get("instructorLa")), pattern));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

