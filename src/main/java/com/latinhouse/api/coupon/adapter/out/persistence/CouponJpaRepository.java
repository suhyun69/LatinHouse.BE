package com.latinhouse.api.coupon.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface CouponJpaRepository extends JpaRepository<CouponEntity, Long> {
}
