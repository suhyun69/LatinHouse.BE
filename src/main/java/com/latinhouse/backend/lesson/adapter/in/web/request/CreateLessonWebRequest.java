package com.latinhouse.backend.lesson.adapter.in.web.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor // 파라미터 딱 하나 있을 때 필요
public class CreateLessonWebRequest {
    @NotNull(message = "title cannot be null")
    private String title;

    @NotNull(message = "genre cannot be null")
    @Pattern(regexp = "[SB]", message = "genre should be 'S' or 'B'")
    private String genre;

    @NotNull(message = "instructor1 cannot be null.")
    private String instructor1;

    private String instructor2;

    @NotNull(message = "startDate cannot be null.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "startDate should be 'yyyy-MM-dd'")
    private String startDate;

    @NotNull(message = "endDate cannot be null.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "endDate should be 'yyyy-MM-dd'")
    private String endDate;

    @NotNull(message = "startTime cannot be null.")
    @Pattern(regexp = "\\d{2}:\\d{2}", message = "startTime should be 'HH:mm'")
    private String startTime;

    @NotNull(message = "endTime cannot be null.")
    @Pattern(regexp = "\\d{2}:\\d{2}", message = "endTime should be 'HH:mm'")
    private String endTime;

    private String dateTimeSummary;

    @NotNull(message = "region cannot be null.")
    @Pattern(regexp = "(GN|HD)", message = "region should be 'GN' or 'HD'")
    private String region;

    @NotNull(message = "location cannot be null.")
    private String location;

    private List<Price> prices;

    private List<Discount> discounts;

    private List<Contact> contacts;

    private List<Account> accounts;

    private List<Notice> notices;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor // 파라미터 딱 하나 있을 때 필요
    public static class Price {

        @NotNull(message = "price.sex cannot be null.")
        @Pattern(regexp = "(M|F)", message = "price.sex should be 'M' or 'F'.")
        private String sex;

        @NotNull(message = "price.price cannot be null.")
        @Min(value = 0, message = "price.price should larger than 0.")
        private BigDecimal price;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor // 파라미터 딱 하나 있을 때 필요
    public static class Discount {

        @NotNull(message = "discount.type cannot be null.")
        @Pattern(regexp = "(E)", message = "price.type should be 'E'.")
        private String type;

        @NotNull(message = "discount.condition cannot be null.")
        private String condition;

        @NotNull(message = "discount.price cannot be null.")
        @Min(value = 0, message = "discount.price should larger than 0.")
        private BigDecimal amount;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor // 파라미터 딱 하나 있을 때 필요
    public static class Contact {

        @NotNull(message = "contact.type cannot be null.")
        @Pattern(regexp = "(P|K)", message = "price.type should be 'P' or 'K'.")
        private String type;

        @NotNull(message = "contact.name cannot be null.")
        private String name;

        @NotNull(message = "contact.contact cannot be null.")
        private String contact;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor // 파라미터 딱 하나 있을 때 필요
    public static class Account {

        @NotNull(message = "account.bank cannot be null.")
        private String bank;

        @NotNull(message = "account.account cannot be null.")
        private String account;

        @NotNull(message = "account.name cannot be null.")
        private String name;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor // 파라미터 딱 하나 있을 때 필요
    public static class Notice {

        @NotNull(message = "notice.type cannot be null.")
        @Pattern(regexp = "(P|D|N)", message = "notice.type should be 'P' or 'D' or 'N'.")
        private String type;

        @NotNull(message = "notice.notice cannot be null.")
        private String notice;
    }
}