package com.latinhouse.api.lesson.application.port.in;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class GetLessonsAppResponse {
    private final Long optionId;
    private final Long lessonNo;
    private final String instructorLo;
    private final String instructorLa;
    private final String title;
    private final String genre;
    private final String startDate;
    private final String startTime;
    private final String endDate;
    private final String endTime;
    private final String region;
    private final BigDecimal price;
    private final String discountCondition;
    private final BigDecimal discountAmount;
    private final String status;
}
