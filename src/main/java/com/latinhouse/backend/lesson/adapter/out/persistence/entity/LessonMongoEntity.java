package com.latinhouse.backend.lesson.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document("lesson")
@Data
@Builder // mapToJpaEntity
@NoArgsConstructor
@AllArgsConstructor
public class LessonMongoEntity {

    @Id
    private String id;

    private Long no;
    private String title;
    private String genre;
    private String instructor1;
    private String instructor2;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String dateTimeSummary;
    private String region;
    private String location;
    private List<LessonPriceMongoEntity> prices = new ArrayList<>();
    private List<LessonDiscountMongoEntity> discounts = new ArrayList<>();
    private List<LessonContactMongoEntity> contacts = new ArrayList<>();
    private List<LessonAccountMongoEntity> accounts = new ArrayList<>();
    private List<LessonNoticeMongoEntity> notices = new ArrayList<>();
}
