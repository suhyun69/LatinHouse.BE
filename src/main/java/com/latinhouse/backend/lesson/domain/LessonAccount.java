package com.latinhouse.backend.lesson.domain;

import lombok.Builder;
import lombok.Value;

@Value // 객체 불변(Immutable) => @Setter 생성되지 않음
@Builder // mapToDomainEntity
public class LessonAccount {
    private String bank;
    private String account;
    private String name;
}