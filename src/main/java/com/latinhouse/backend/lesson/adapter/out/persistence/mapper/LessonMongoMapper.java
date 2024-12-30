package com.latinhouse.backend.lesson.adapter.out.persistence.mapper;

import com.latinhouse.backend.lesson.adapter.out.persistence.entity.*;
import com.latinhouse.backend.lesson.domain.*;
import com.latinhouse.backend.lesson.port.in.request.CreateLessonAppRequest;
import com.latinhouse.backend.profile.domain.Sex;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LessonMongoMapper {

    public LessonMongoEntity mapToMongoEntity(CreateLessonAppRequest req) {

        List<LessonPriceMongoEntity> lessonPriceMongoEntities = Optional.ofNullable(req.getPrices())
                .filter(prices -> !prices.isEmpty())
                .map(prices -> prices.stream().map(this::mapToMongoEntity).collect(Collectors.toList()))
                .orElseGet(ArrayList::new);

        List<LessonDiscountMongoEntity> lessonDiscountMongoEntities = Optional.ofNullable(req.getDiscounts())
                .filter(discounts -> !discounts.isEmpty())
                .map(discounts -> discounts.stream().map(this::mapToMongoEntity).collect(Collectors.toList()))
                .orElseGet(ArrayList::new);

        List<LessonContactMongoEntity> lessonContactMongoEntities = Optional.ofNullable(req.getContacts())
                .filter(contacts -> !contacts.isEmpty())
                .map(contacts -> contacts.stream().map(this::mapToMongoEntity).collect(Collectors.toList()))
                .orElseGet(ArrayList::new);

        List<LessonAccountMongoEntity> lessonAccountMongoEntities = Optional.ofNullable(req.getAccounts())
                .filter(accounts -> !accounts.isEmpty())
                .map(accounts -> accounts.stream().map(this::mapToMongoEntity).collect(Collectors.toList()))
                .orElseGet(ArrayList::new);

        List<LessonNoticeMongoEntity> lessonNoticeMongoEntities = Optional.ofNullable(req.getNotices())
                .filter(notices -> !notices.isEmpty())
                .map(notices -> notices.stream().map(this::mapToMongoEntity).collect(Collectors.toList()))
                .orElseGet(ArrayList::new);

        return LessonMongoEntity.builder()
                .title(req.getTitle())
                .genre(req.getGenre().code)
                .instructor1(req.getInstructor1())
                .instructor2(req.getInstructor2())
                .startDateTime(req.getStartDateTime())
                .endDateTime(req.getEndDateTime())
                .dateTimeSummary(req.getDateTimeSummary())
                .region(req.getRegion().code)
                .location(req.getLocation())
                .prices(lessonPriceMongoEntities)
                .discounts(lessonDiscountMongoEntities)
                .contacts(lessonContactMongoEntities)
                .accounts(lessonAccountMongoEntities)
                .notices(lessonNoticeMongoEntities)
                .build();
    }

    public Lesson mapToDomainEntity(LessonMongoEntity lessonT) {
        return Lesson.builder()
                .no(lessonT.getNo())
                .title(lessonT.getTitle())
                .genre(Genre.of(lessonT.getGenre()))
                .instructor1(lessonT.getInstructor1())
                .instructor2(lessonT.getInstructor2())
                .startDateTime(lessonT.getStartDateTime())
                .endDateTime(lessonT.getEndDateTime())
                .datTimeSummary(lessonT.getDateTimeSummary())
                .region(Region.of(lessonT.getRegion()))
                .location(lessonT.getLocation())
                .prices(CollectionUtils.isEmpty(lessonT.getPrices()) ? new ArrayList<>()
                        : lessonT.getPrices().stream().map(this::mapToDomainEntity).collect(Collectors.toList()))
                .discounts(CollectionUtils.isEmpty(lessonT.getDiscounts()) ? new ArrayList<>()
                        : lessonT.getDiscounts().stream().map(this::mapToDomainEntity).collect(Collectors.toList()))
                .contacts(CollectionUtils.isEmpty(lessonT.getContacts()) ? new ArrayList<>()
                        : lessonT.getContacts().stream().map(this::mapToDomainEntity).collect(Collectors.toList()))
                .accounts(CollectionUtils.isEmpty(lessonT.getAccounts()) ? new ArrayList<>()
                        : lessonT.getAccounts().stream().map(this::mapToDomainEntity).collect(Collectors.toList()))
                .notices(CollectionUtils.isEmpty(lessonT.getNotices()) ? new ArrayList<>()
                        : lessonT.getNotices().stream().map(this::mapToDomainEntity).collect(Collectors.toList()))
                .build();
    }

    public LessonPriceMongoEntity mapToMongoEntity(CreateLessonAppRequest.Price req) {
        return LessonPriceMongoEntity.builder()
                .sex(req.getSex().getCode())
                .price(req.getPrice())
                .build();
    }

    public LessonPrice mapToDomainEntity(LessonPriceMongoEntity priceT) {
        return LessonPrice.builder()
                .sex(Sex.of(priceT.getSex()))
                .price(priceT.getPrice())
                .build();
    }

    public LessonDiscountMongoEntity mapToMongoEntity(CreateLessonAppRequest.Discount req) {
        return LessonDiscountMongoEntity.builder()
                .type(req.getType().code)
                .condition(req.getCondition())
                .amount(req.getAmount())
                .build();
    }

    public LessonDiscount mapToDomainEntity(LessonDiscountMongoEntity discountT) {
        return LessonDiscount.builder()
                .type(DiscountType.of(discountT.getType()))
                .condition(discountT.getCondition())
                .amount(discountT.getAmount())
                .build();
    }

    public LessonContactMongoEntity mapToMongoEntity(CreateLessonAppRequest.Contact req) {
        return LessonContactMongoEntity.builder()
                .type(req.getType().code)
                .name(req.getName())
                .contact(req.getContact())
                .build();
    }

    public LessonContact mapToDomainEntity(LessonContactMongoEntity contactT) {
        return LessonContact.builder()
                .type(ContactType.of(contactT.getType()))
                .name(contactT.getName())
                .contact(contactT.getContact())
                .build();
    }

    public LessonAccountMongoEntity mapToMongoEntity(CreateLessonAppRequest.Account req) {
        return LessonAccountMongoEntity.builder()
                .bank(req.getBank())
                .account(req.getAccount())
                .name(req.getName())
                .build();
    }

    public LessonAccount mapToDomainEntity(LessonAccountMongoEntity accountT) {
        return LessonAccount.builder()
                .bank(accountT.getBank())
                .account(accountT.getAccount())
                .name(accountT.getName())
                .build();
    }

    public LessonNoticeMongoEntity mapToMongoEntity(CreateLessonAppRequest.Notice req) {
        return LessonNoticeMongoEntity.builder()
                .type(req.getType().code)
                .notice(req.getNotice())
                .build();
    }

    public LessonNotice mapToDomainEntity(LessonNoticeMongoEntity noticeT) {
        return LessonNotice.builder()
                .type(NoticeType.of(noticeT.getType()))
                .notice(noticeT.getNotice())
                .build();
    }
}