package com.latinhouse.api.lesson.adapter.in.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateLessonWebRequest {

    @NotBlank(message = "제목을 입력해 주세요.")
    private String title;

    @NotBlank(message = "장르를 입력해 주세요.")
    @Pattern(regexp = "^[SB]$", message = "장르는 S 또는 B만 입력 가능합니다.")
    private String genre;

    private String instructorLo;
    private String instructorLa;

    @NotEmpty(message = "수업 옵션을 1개 이상 입력해 주세요.")
    @Valid
    private List<OptionWebReq> options;

    private BigDecimal amount;

    @Valid
    private List<DiscountWebReq> discounts;

    private AccountWebReq account;

    @Valid
    private List<ContactWebReq> contacts;

    private Boolean isActive;

    @Valid
    private List<NoticeWebReq> notices;

    @Getter
    @NoArgsConstructor
    public static class OptionWebReq {

        private Long id;

        @NotBlank(message = "시작 날짜를 입력해 주세요.")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "시작 날짜는 yyyy-MM-dd 형식으로 입력해 주세요.")
        private String startDate;

        @NotBlank(message = "시작 시간을 입력해 주세요.")
        @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "시작 시간은 HH:mm 형식으로 입력해 주세요.")
        private String startTime;

        @NotBlank(message = "종료 날짜를 입력해 주세요.")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "종료 날짜는 yyyy-MM-dd 형식으로 입력해 주세요.")
        private String endDate;

        @NotBlank(message = "종료 시간을 입력해 주세요.")
        @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "종료 시간은 HH:mm 형식으로 입력해 주세요.")
        private String endTime;

        @NotBlank(message = "지역을 입력해 주세요.")
        @Pattern(regexp = "^(GN|HD)$", message = "지역은 GN 또는 HD만 입력 가능합니다.")
        private String region;

        private String place;
        private String placeUrl;
    }

    @Getter
    @NoArgsConstructor
    public static class DiscountWebReq {

        private Long id;

        @Pattern(regexp = "^[ES]$", message = "할인 타입은 E 또는 S만 입력 가능합니다.")
        private String type;

        private String condition;
        private BigDecimal amount;
    }

    @Getter
    @NoArgsConstructor
    public static class AccountWebReq {
        private Long id;
        private String bank;
        private String account;
        private String name;
    }

    @Getter
    @NoArgsConstructor
    public static class ContactWebReq {

        private Long id;

        @Pattern(regexp = "^[YKWILM]$", message = "연락처 타입이 올바르지 않습니다.")
        private String type;

        private String account;
        private String name;
    }

    @Getter
    @NoArgsConstructor
    public static class NoticeWebReq {

        private Long id;

        @Pattern(regexp = "^[LTRNU]$", message = "공지 타입이 올바르지 않습니다.")
        private String type;

        private String text;
    }
}
