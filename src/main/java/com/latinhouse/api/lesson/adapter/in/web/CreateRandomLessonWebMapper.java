package com.latinhouse.api.lesson.adapter.in.web;

import com.latinhouse.api.lesson.application.port.in.CreateRandomLessonAppResponse;

public class CreateRandomLessonWebMapper {

    private CreateRandomLessonWebMapper() {}

    public static CreateRandomLessonWebResponse toWebResponse(CreateRandomLessonAppResponse appResponse) {
        return CreateRandomLessonWebResponse.builder()
                .id(appResponse.getId())
                .build();
    }
}
