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
@Table(name = "lesson_notice")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LessonNoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false, length = 1)
    private String type;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @Builder
    public LessonNoticeEntity(Long id, String type, String text) {
        this.id = id;
        this.type = type;
        this.text = text;
    }
}
