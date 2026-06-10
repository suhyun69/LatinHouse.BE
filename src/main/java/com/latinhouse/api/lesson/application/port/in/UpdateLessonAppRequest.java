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
public class UpdateLessonAppRequest {
    private final Long lessonNo;
    private final String title;
    private final Genre genre;
    private final String instructorLo;
    private final String instructorLa;
    private final List<OptionAppReq> options;
    private final BigDecimal amount;
    private final List<DiscountAppReq> discounts;
    private final AccountAppReq account;
    private final List<ContactAppReq> contacts;
    private final Boolean isActive;
    private final List<NoticeAppReq> notices;

    @Getter
    @Builder
    public static class OptionAppReq {
        private final Long id;
        private final LocalDateTime startDateTime;
        private final LocalDateTime endDateTime;
        private final Region region;
        private final String place;
        private final String placeUrl;
    }

    @Getter
    @Builder
    public static class DiscountAppReq {
        private final Long id;
        private final DiscountType type;
        private final String condition;
        private final BigDecimal amount;
    }

    @Getter
    @Builder
    public static class AccountAppReq {
        private final Long id;
        private final String bank;
        private final String account;
        private final String name;
    }

    @Getter
    @Builder
    public static class ContactAppReq {
        private final Long id;
        private final ContactType type;
        private final String account;
        private final String name;
    }

    @Getter
    @Builder
    public static class NoticeAppReq {
        private final Long id;
        private final NoticeType type;
        private final String text;
    }
}
