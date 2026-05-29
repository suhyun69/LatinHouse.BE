package com.latinhouse.api.lesson.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LessonContact {
    private final Long id;
    private final ContactType type;
    private final String account;
    private final String name;
}
