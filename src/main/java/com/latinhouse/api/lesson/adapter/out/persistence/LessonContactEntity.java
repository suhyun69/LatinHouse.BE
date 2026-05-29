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
@Table(name = "lesson_contact")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LessonContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false, length = 1)
    private String type;

    @Column(name = "account")
    private String account;

    @Column(name = "name")
    private String name;

    @Builder
    public LessonContactEntity(Long id, String type, String account, String name) {
        this.id = id;
        this.type = type;
        this.account = account;
        this.name = name;
    }
}
