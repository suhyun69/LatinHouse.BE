package com.latinhouse.api.lesson.adapter.out.persistence;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lesson")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "genre", nullable = false, length = 1)
    private String genre;

    @Column(name = "instructor_lo", length = 8)
    private String instructorLo;

    @Column(name = "instructor_la", length = 8)
    private String instructorLa;

    @Column(name = "amount", precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lesson_id")
    private List<LessonOptionEntity> options = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lesson_id")
    private List<LessonDiscountEntity> discounts = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private LessonAccountEntity account;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lesson_id")
    private List<LessonContactEntity> contacts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lesson_id")
    private List<LessonNoticeEntity> notices = new ArrayList<>();

    @Builder
    public LessonEntity(Long id, String title, String genre, String instructorLo, String instructorLa,
                        BigDecimal amount, boolean isActive,
                        List<LessonOptionEntity> options, List<LessonDiscountEntity> discounts,
                        LessonAccountEntity account, List<LessonContactEntity> contacts,
                        List<LessonNoticeEntity> notices) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.instructorLo = instructorLo;
        this.instructorLa = instructorLa;
        this.amount = amount;
        this.isActive = isActive;
        if (options != null) this.options = options;
        if (discounts != null) this.discounts = discounts;
        this.account = account;
        if (contacts != null) this.contacts = contacts;
        if (notices != null) this.notices = notices;
    }
}
