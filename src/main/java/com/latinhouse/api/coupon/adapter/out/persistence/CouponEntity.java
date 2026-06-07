package com.latinhouse.api.coupon.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupons")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_id", nullable = false)
    private Long templateId;

    @Column(name = "owner", length = 8)
    private String owner;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Builder
    public CouponEntity(Long id, Long templateId, String owner, String status) {
        this.id = id;
        this.templateId = templateId;
        this.owner = owner;
        this.status = status;
    }

    public void updateOwner(String owner) {
        this.owner = owner;
    }
}
