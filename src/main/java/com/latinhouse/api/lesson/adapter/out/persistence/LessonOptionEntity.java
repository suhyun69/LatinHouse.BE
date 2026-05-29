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

import java.time.LocalDateTime;

@Entity
@Table(name = "lesson_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LessonOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;

    @Column(name = "region", nullable = false, length = 2)
    private String region;

    @Column(name = "place")
    private String place;

    @Column(name = "place_url")
    private String placeUrl;

    @Builder
    public LessonOptionEntity(Long id, LocalDateTime startDateTime, LocalDateTime endDateTime,
                               String region, String place, String placeUrl) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.region = region;
        this.place = place;
        this.placeUrl = placeUrl;
    }
}
