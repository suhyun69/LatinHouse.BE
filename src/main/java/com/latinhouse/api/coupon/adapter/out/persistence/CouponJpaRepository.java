package com.latinhouse.api.coupon.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface CouponJpaRepository extends JpaRepository<CouponEntity, Long> {
    List<CouponEntity> findByOwner(String owner);
}
