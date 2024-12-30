package com.latinhouse.backend.lesson.adapter.out.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("lessons_sequence")
@Data
public class LessonSequence {
    @Id
    private String id;
    private long sequence;
}