package com.latinhouse.backend.lesson.port.in.request;

import com.latinhouse.backend.common.SelfValidating;
import com.latinhouse.backend.lesson.adapter.in.web.request.CreateLessonWebRequest;
import com.latinhouse.backend.lesson.domain.*;
import com.latinhouse.backend.profile.domain.Sex;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Value
@EqualsAndHashCode(callSuper = false)
public class CreateLessonAppRequest extends SelfValidating<CreateLessonAppRequest> {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @NotNull(message = "title cannot be null.")
    private String title;

    @NotNull(message = "genre cannot be null.")
    private Genre genre;

    @NotNull(message = "instructor1 cannot be null.")
    private String instructor1;

    private String instructor2;

    @NotNull(message = "startDateTime(는) null일 수 없습니다.")
    private LocalDateTime startDateTime;

    @NotNull(message = "endDateTime(는) null일 수 없습니다.")
    private LocalDateTime endDateTime;

    private String dateTimeSummary;

    @NotNull(message = "region cannot be null.")
    private Region region;

    @NotNull(message = "location cannot be null.")
    private String location;

    private List<Price> prices;

    private List<Discount> discounts;

    private List<Contact> contacts;

    private List<Account> accounts;

    private List<Notice> notices;

    public CreateLessonAppRequest(CreateLessonWebRequest webReq) {
        this.title = webReq.getTitle();
        this.genre = Genre.of(webReq.getGenre());
        this.instructor1 = webReq.getInstructor1();
        this.instructor2 = webReq.getInstructor2();
        this.startDateTime = LocalDateTime.parse(String.format("%s %s", webReq.getStartDate(), webReq.getStartTime()), dateTimeFormatter);
        this.endDateTime = LocalDateTime.parse(String.format("%s %s", webReq.getEndDate(), webReq.getEndTime()), dateTimeFormatter);
        this.dateTimeSummary = webReq.getDateTimeSummary();
        this.region = Region.of(webReq.getRegion());
        this.location = webReq.getLocation();
        this.prices = CollectionUtils.isEmpty(webReq.getPrices()) ? new ArrayList<>() : webReq.getPrices().stream().map(Price::new).collect(Collectors.toList());
        this.discounts = CollectionUtils.isEmpty(webReq.getDiscounts()) ? new ArrayList<>() : webReq.getDiscounts().stream().map(Discount::new).collect(Collectors.toList());
        this.contacts = CollectionUtils.isEmpty(webReq.getContacts()) ? new ArrayList<>() : webReq.getContacts().stream().map(Contact::new).collect(Collectors.toList());
        this.accounts = CollectionUtils.isEmpty(webReq.getAccounts()) ? new ArrayList<>() : webReq.getAccounts().stream().map(Account::new).collect(Collectors.toList());
        this.notices = CollectionUtils.isEmpty(webReq.getNotices()) ? new ArrayList<>() : webReq.getNotices().stream().map(Notice::new).collect(Collectors.toList());
        this.validateSelf();
    }

    @Value
    @EqualsAndHashCode(callSuper = false)
    public static class Price extends SelfValidating<Price> {

        @NotNull(message = "price.sex cannot be null.")
        private Sex sex;

        @NotNull(message = "prcie.price cannot be null.")
        @Min(value = 0, message = "prcie.price should larger than 0.")
        private BigDecimal price;

        public Price(CreateLessonWebRequest.Price price) {
            this.sex = Sex.of(price.getSex());
            this.price = price.getPrice();
            this.validateSelf();
        }
    }

    @Value
    @EqualsAndHashCode(callSuper = false)
    public static class Discount extends SelfValidating<Discount> {

        @NotNull(message = "discount.type cannot be null.")
        private DiscountType type;

        @NotNull(message = "discount.condition cannot be null.")
        private String condition;

        @NotNull(message = "discount.price cannot be null.")
        @Min(value = 0, message = "discount.price should larger than 0.")
        private BigDecimal amount;

        public Discount(CreateLessonWebRequest.Discount discount) {
            this.type = DiscountType.of(discount.getType());
            this.condition = discount.getCondition();
            this.amount = discount.getAmount();
            this.validateSelf();
        }
    }

    @Value
    @EqualsAndHashCode(callSuper = false)
    public static class Contact extends SelfValidating<Contact> {

        @NotNull(message = "contact.type cannot be null.")
        private ContactType type;

        @NotNull(message = "contact.name cannot be null.")
        private String name;

        @NotNull(message = "contact.contact cannot be null.")
        private String contact;

        public Contact(CreateLessonWebRequest.Contact contact) {
            this.type = ContactType.of(contact.getType());
            this.name = contact.getName();
            this.contact = contact.getContact();
            this.validateSelf();
        }
    }

    @Value
    @EqualsAndHashCode(callSuper = false)
    public static class Account extends SelfValidating<Account> {

        @NotNull(message = "account.type cannot be null.")
        private String bank;

        @NotNull(message = "account.account cannot be null.")
        private String account;

        @NotNull(message = "account.name cannot be null.")
        private String name;

        public Account(CreateLessonWebRequest.Account account) {
            this.bank = account.getBank();
            this.account = account.getAccount();
            this.name = account.getName();
            this.validateSelf();
        }
    }

    @Value
    @EqualsAndHashCode(callSuper = false)
    public static class Notice extends SelfValidating<Notice> {

        @NotNull(message = "notice.type cannot be null.")
        private NoticeType type;

        @NotNull(message = "notice.notice cannot be null.")
        private String notice;

        public Notice(CreateLessonWebRequest.Notice n) {
            this.type = NoticeType.of(n.getType());
            this.notice = n.getNotice();
            this.validateSelf();
        }
    }
}