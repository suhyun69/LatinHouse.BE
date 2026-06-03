package com.latinhouse.api.lesson.application.port.in;

import com.latinhouse.api.lesson.domain.Genre;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class UpdateLessonAppRequest {
    private final Long lessonNo;
    private final String title;
    private final Genre genre;
    private final String instructorLo;
    private final String instructorLa;
    private final List<CreateLessonAppRequest.OptionAppReq> options;
    private final BigDecimal amount;
    private final List<CreateLessonAppRequest.DiscountAppReq> discounts;
    private final CreateLessonAppRequest.AccountAppReq account;
    private final List<CreateLessonAppRequest.ContactAppReq> contacts;
    private final Boolean isActive;
    private final List<CreateLessonAppRequest.NoticeAppReq> notices;
}
