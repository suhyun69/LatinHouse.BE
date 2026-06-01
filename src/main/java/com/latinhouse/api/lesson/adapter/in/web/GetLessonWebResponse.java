package com.latinhouse.api.lesson.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class GetLessonWebResponse {

    private final Long id;
    private final String title;
    private final String genre;
    private final String instructorLo;
    private final String instructorLa;
    private final List<OptionResponse> options;
    private final BigDecimal amount;
    private final List<DiscountResponse> discounts;
    private final AccountResponse account;
    private final List<ContactResponse> contacts;
    @JsonProperty("isActive")
    private final boolean isActive;
    private final List<NoticeResponse> notices;

    @Getter
    @Builder
    public static class OptionResponse {
        private final Long id;
        private final String startDate;
        private final String startTime;
        private final String endDate;
        private final String endTime;
        private final String region;
        private final String place;
        private final String placeUrl;
    }

    @Getter
    @Builder
    public static class DiscountResponse {
        private final Long id;
        private final String type;
        private final String condition;
        private final BigDecimal amount;
    }

    @Getter
    @Builder
    public static class AccountResponse {
        private final Long id;
        private final String bank;
        private final String account;
        private final String name;
    }

    @Getter
    @Builder
    public static class ContactResponse {
        private final Long id;
        private final String type;
        private final String account;
        private final String name;
    }

    @Getter
    @Builder
    public static class NoticeResponse {
        private final Long id;
        private final String type;
        private final String text;
    }
}
