package com.latinhouse.api.lesson.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LessonAccount {
    private final Long id;
    private final String bank;
    private final String account;
    private final String name;
}
