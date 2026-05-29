package com.latinhouse.api.lesson.adapter.out.persistence;

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
@Table(name = "lesson_account")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LessonAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bank")
    private String bank;

    @Column(name = "account")
    private String account;

    @Column(name = "name")
    private String name;

    @Builder
    public LessonAccountEntity(Long id, String bank, String account, String name) {
        this.id = id;
        this.bank = bank;
        this.account = account;
        this.name = name;
    }
}
