package com.latinhouse.api.lesson.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LessonOption {
    private final Long id;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final Region region;
    private final String place;
    private final String placeUrl;
}
