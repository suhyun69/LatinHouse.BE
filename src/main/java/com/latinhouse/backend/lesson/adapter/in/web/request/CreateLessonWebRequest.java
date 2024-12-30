package com.latinhouse.backend.lesson.adapter.in.web.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateLessonWebRequest {

    @NotNull(message = "title cannot be null.")
    private String title;
}
