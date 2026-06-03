package com.latinhouse.api.lesson.application.port.in;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateLessonAppResponse {
    private final Long id;
}
