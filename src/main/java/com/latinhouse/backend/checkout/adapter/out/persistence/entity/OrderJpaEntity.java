package com.latinhouse.backend.checkout.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@Builder // mapToJpaEntity
@NoArgsConstructor
@AllArgsConstructor
public class OrderJpaEntity {

    @Id
    @Column(name = "ORDER_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long seq;
    private Long lessonNo;
    private String profileId;
}
