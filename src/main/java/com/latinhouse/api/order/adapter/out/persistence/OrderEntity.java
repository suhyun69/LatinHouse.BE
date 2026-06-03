package com.latinhouse.api.order.adapter.out.persistence;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "lesson_no", nullable = false)
    private Long lessonNo;

    @Column(name = "lesson_option_no", nullable = false)
    private Long lessonOptionNo;

    @Column(name = "buyer", nullable = false, length = 8)
    private String buyer;

    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderDiscountEntity> discounts = new ArrayList<>();

    @Builder
    public OrderEntity(String id, Long lessonNo, Long lessonOptionNo, String buyer,
                       BigDecimal price, Long paymentId, String status,
                       List<OrderDiscountEntity> discounts) {
        this.id = id;
        this.lessonNo = lessonNo;
        this.lessonOptionNo = lessonOptionNo;
        this.buyer = buyer;
        this.price = price;
        this.paymentId = paymentId;
        this.status = status;
        if (discounts != null) this.discounts = discounts;
    }
}
