package com.latinhouse.backend.lesson.port.in.response;

import com.latinhouse.backend.lesson.domain.*;
import com.latinhouse.backend.profile.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public abstract class GetLessonAppResponse {
    private Long lessonNo;
    private String title;
    private String genre;
    private GetLessonAppResponse.ProfileSummaryInfo instructor1;
    private GetLessonAppResponse.ProfileSummaryInfo instructor2;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    // private String dateTimeSummary;
    private String region;
    // private String location;
    private List<GetLessonAppResponse.LessonPriceInfo> prices;
    private List<GetLessonAppResponse.LessonDiscountInfo> discounts;
    // private List<LessonInfo.LessonContactInfo> contacts;
    // private List<LessonInfo.LessonAccountInfo> accounts;
    // private List<LessonInfo.LessonNoticeInfo> notices;

    public GetLessonAppResponse(Lesson l, Profile instructor1, Profile instructor2) {
        this.lessonNo = l.getNo();
        this.title = l.getTitle();
        this.genre = l.getGenre().name();
        this.instructor1 = new GetLessonAppResponse.ProfileSummaryInfo(instructor1);
        this.instructor2 = Optional.ofNullable(instructor2).map(GetLessonAppResponse.ProfileSummaryInfo::new).orElse(null);
        this.startDate = l.getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.endDate = l.getEndDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.startTime = l.getStartDateTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.endTime = l.getEndDateTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        // this.dateTimeSummary = l.getDatTimeSummary();
        this.region = l.getRegion().name();
        // this.location = l.getLocation();
        this.prices = l.getPrices().stream().map(GetLessonAppResponse.LessonPriceInfo::new).collect(Collectors.toList());
        this.discounts = l.getDiscounts().stream().map(GetLessonAppResponse.LessonDiscountInfo::new).collect(Collectors.toList());
        // this.contacts = l.getContacts().stream().map(GetLessonAppResponse.LessonContactInfo::new).collect(Collectors.toList());
        // this.accounts = l.getAccounts().stream().map(GetLessonAppResponse.LessonAccountInfo::new).collect(Collectors.toList());
        // this.notices = l.getNotices().stream().map(GetLessonAppResponse.LessonNoticeInfo::new).collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor // test
    public static class ProfileSummaryInfo {
        private String id;
        private String nickname;

        public ProfileSummaryInfo(Profile p) {
            this.id = p.getId();
            this.nickname = p.getNickname();
        }
    }

    @Getter
    @AllArgsConstructor // test
    public static class LessonPriceInfo {
        private String sex;
        private BigDecimal price;

        public LessonPriceInfo(LessonPrice p) {
            this.sex = p.getSex().name();
            this.price = p.getPrice();
        }
    }

    @Getter
    @AllArgsConstructor // test
    public static class LessonDiscountInfo {
        private String type;
        private String condition;
        private BigDecimal amount;

        public LessonDiscountInfo(LessonDiscount d) {
            this.type = d.getType().name();
            this.condition = d.getCondition();
            this.amount = d.getAmount();
        }
    }

    /*
    @Getter
    @AllArgsConstructor // test
    public static class LessonContactInfo {
        private String type;
        private String name;
        private String contact;

        public LessonContactInfo(LessonContact c) {
            this.type = c.getType().name();
            this.name = c.getName();
            this.contact = c.getContact();
        }
    }
    */

    /*
    @Getter
    @AllArgsConstructor // test
    public static class LessonAccountInfo {
        private String bank;
        private String account;
        private String name;

        public LessonAccountInfo(LessonAccount a) {
            this.bank = a.getBank();
            this.account = a.getAccount();
            this.name = a.getName();
        }
    }
    */

    /*
    @Getter
    @AllArgsConstructor // test
    public static class LessonNoticeInfo {
        private String type;
        private String notice;

        public LessonNoticeInfo(LessonNotice n) {
            this.type = n.getType().name();
            this.notice = n.getNotice();
        }
    }
    */
}
