package com.latinhouse.backend.lesson.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lesson")
@Data
@Builder // mapToJpaEntity
@NoArgsConstructor
@AllArgsConstructor
public class LessonJpaEntity {
    @Id
    @Column(name = "LESSON_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long no;
    private String title;
}
