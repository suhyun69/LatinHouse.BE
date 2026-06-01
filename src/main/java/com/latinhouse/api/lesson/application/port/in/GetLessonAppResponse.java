package com.latinhouse.api.lesson.application.port.in;

import com.latinhouse.api.lesson.domain.ContactType;
import com.latinhouse.api.lesson.domain.DiscountType;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.NoticeType;
import com.latinhouse.api.lesson.domain.Region;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetLessonAppResponse {

    private final Long id;
    private final String title;
    private final Genre genre;
    private final String instructorLo;
    private final String instructorLa;
    private final List<OptionResponse> options;
    private final BigDecimal amount;
    private final List<DiscountResponse> discounts;
    private final AccountResponse account;
    private final List<ContactResponse> contacts;
    private final boolean isActive;
    private final List<NoticeResponse> notices;

    @Getter
    @Builder
    public static class OptionResponse {
        private final Long id;
        private final LocalDateTime startDateTime;
        private final LocalDateTime endDateTime;
        private final Region region;
        private final String place;
        private final String placeUrl;
    }

    @Getter
    @Builder
    public static class DiscountResponse {
        private final Long id;
        private final DiscountType type;
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
        private final ContactType type;
        private final String account;
        private final String name;
    }

    @Getter
    @Builder
    public static class NoticeResponse {
        private final Long id;
        private final NoticeType type;
        private final String text;
    }
}
