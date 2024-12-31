package com.latinhouse.backend.checkout.adapter.in.web.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnrollLessonWebRequest {
    private Long lessonNo;
    private String profileId;
}
