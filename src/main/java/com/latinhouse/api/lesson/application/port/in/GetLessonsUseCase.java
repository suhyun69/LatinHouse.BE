package com.latinhouse.api.lesson.application.port.in;

import java.util.List;

public interface GetLessonsUseCase {
    List<GetLessonsAppResponse> getLessons(GetLessonsAppRequest request);
}
