package com.latinhouse.backend.lesson.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value // 객체 불변(Immutable) => @Setter 생성되지 않음
@Builder // mapToDomainEntity
public class Lesson {
    Long no;
    String title;
    Genre genre;
    String instructor1;
    String instructor2;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    String datTimeSummary;
    Region region;
    String location;
    List<LessonPrice> prices;
    List<LessonDiscount> discounts;
    List<LessonContact> contacts;
    List<LessonAccount> accounts;
    List<LessonNotice> notices;
}