package com.latinhouse.api.lesson.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LessonNotice {
    private final Long id;
    private final NoticeType type;
    private final String text;
}
