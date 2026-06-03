package com.latinhouse.api.lesson.application.port.in;

import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.Region;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetLessonsAppRequest {
    private final Region region;
    private final String instructor;
    private final Genre genre;
}
