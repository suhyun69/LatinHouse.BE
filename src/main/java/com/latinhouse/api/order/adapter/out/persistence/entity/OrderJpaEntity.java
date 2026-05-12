package com.latinhouse.api.order.adapter.out.persistence.entity;

import com.latinhouse.api.lesson.adapter.out.persistence.entity.LessonJpaEntity;
import com.latinhouse.api.order.domain.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class OrderJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private Long lessonNo;
    private Long optionNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lessonNo", insertable = false, updatable = false)
    private LessonJpaEntity lesson;
    private String profileId;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private String createdBy;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private String modifiedBy;
}
