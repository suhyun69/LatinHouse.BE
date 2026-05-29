package com.latinhouse.api.lesson.application.port.in;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateLessonAppResponse {
    private final Long id;
}
