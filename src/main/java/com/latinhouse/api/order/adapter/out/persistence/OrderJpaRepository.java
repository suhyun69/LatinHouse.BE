package com.latinhouse.api.order.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface OrderJpaRepository extends JpaRepository<OrderEntity, String>,
        JpaSpecificationExecutor<OrderEntity> {
}
