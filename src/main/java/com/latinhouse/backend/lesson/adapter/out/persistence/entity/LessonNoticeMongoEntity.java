package com.latinhouse.backend.lesson.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder // mapToJpaEntity
@NoArgsConstructor
@AllArgsConstructor
public class LessonNoticeMongoEntity {
    private String type;
    private String notice;
}