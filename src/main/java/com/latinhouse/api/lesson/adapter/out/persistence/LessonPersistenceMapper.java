package com.latinhouse.api.lesson.adapter.out.persistence;

import com.latinhouse.api.lesson.domain.ContactType;
import com.latinhouse.api.lesson.domain.DiscountType;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.domain.LessonAccount;
import com.latinhouse.api.lesson.domain.LessonContact;
import com.latinhouse.api.lesson.domain.LessonDiscount;
import com.latinhouse.api.lesson.domain.LessonNotice;
import com.latinhouse.api.lesson.domain.LessonOption;
import com.latinhouse.api.lesson.domain.NoticeType;
import com.latinhouse.api.lesson.domain.Region;

import java.util.Collections;
import java.util.List;

class LessonPersistenceMapper {

    private LessonPersistenceMapper() {}

    static LessonEntity toEntity(Lesson lesson) {
        return LessonEntity.builder()
                .title(lesson.getTitle())
                .genre(lesson.getGenre().getCode())
                .instructorLo(lesson.getInstructorLo())
                .instructorLa(lesson.getInstructorLa())
                .amount(lesson.getAmount())
                .isActive(lesson.isActive())
                .options(toOptionEntities(lesson.getOptions()))
                .discounts(toDiscountEntities(lesson.getDiscounts()))
                .account(toAccountEntity(lesson.getAccount()))
                .contacts(toContactEntities(lesson.getContacts()))
                .notices(toNoticeEntities(lesson.getNotices()))
                .build();
    }

    static Lesson toDomain(LessonEntity entity) {
        return Lesson.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .genre(Genre.fromCode(entity.getGenre()))
                .instructorLo(entity.getInstructorLo())
                .instructorLa(entity.getInstructorLa())
                .amount(entity.getAmount())
                .isActive(entity.isActive())
                .options(toOptionDomains(entity.getOptions()))
                .discounts(toDiscountDomains(entity.getDiscounts()))
                .account(toAccountDomain(entity.getAccount()))
                .contacts(toContactDomains(entity.getContacts()))
                .notices(toNoticeDomains(entity.getNotices()))
                .build();
    }

    private static List<LessonOptionEntity> toOptionEntities(List<LessonOption> options) {
        if (options == null) return Collections.emptyList();
        return options.stream()
                .map(o -> LessonOptionEntity.builder()
                        .startDateTime(o.getStartDateTime())
                        .endDateTime(o.getEndDateTime())
                        .region(o.getRegion().getCode())
                        .place(o.getPlace())
                        .placeUrl(o.getPlaceUrl())
                        .build())
                .toList();
    }

    private static List<LessonDiscountEntity> toDiscountEntities(List<LessonDiscount> discounts) {
        if (discounts == null) return Collections.emptyList();
        return discounts.stream()
                .map(d -> LessonDiscountEntity.builder()
                        .type(d.getType().getCode())
                        .conditionValue(d.getCondition())
                        .amount(d.getAmount())
                        .build())
                .toList();
    }

    private static LessonAccountEntity toAccountEntity(LessonAccount account) {
        if (account == null) return null;
        return LessonAccountEntity.builder()
                .bank(account.getBank())
                .account(account.getAccount())
                .name(account.getName())
                .build();
    }

    private static List<LessonContactEntity> toContactEntities(List<LessonContact> contacts) {
        if (contacts == null) return Collections.emptyList();
        return contacts.stream()
                .map(c -> LessonContactEntity.builder()
                        .type(c.getType().getCode())
                        .account(c.getAccount())
                        .name(c.getName())
                        .build())
                .toList();
    }

    private static List<LessonNoticeEntity> toNoticeEntities(List<LessonNotice> notices) {
        if (notices == null) return Collections.emptyList();
        return notices.stream()
                .map(n -> LessonNoticeEntity.builder()
                        .type(n.getType().getCode())
                        .text(n.getText())
                        .build())
                .toList();
    }

    private static List<LessonOption> toOptionDomains(List<LessonOptionEntity> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream()
                .map(e -> LessonOption.builder()
                        .id(e.getId())
                        .startDateTime(e.getStartDateTime())
                        .endDateTime(e.getEndDateTime())
                        .region(Region.fromCode(e.getRegion()))
                        .place(e.getPlace())
                        .placeUrl(e.getPlaceUrl())
                        .build())
                .toList();
    }

    private static List<LessonDiscount> toDiscountDomains(List<LessonDiscountEntity> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream()
                .map(e -> LessonDiscount.builder()
                        .id(e.getId())
                        .type(DiscountType.fromCode(e.getType()))
                        .condition(e.getConditionValue())
                        .amount(e.getAmount())
                        .build())
                .toList();
    }

    private static LessonAccount toAccountDomain(LessonAccountEntity entity) {
        if (entity == null) return null;
        return LessonAccount.builder()
                .id(entity.getId())
                .bank(entity.getBank())
                .account(entity.getAccount())
                .name(entity.getName())
                .build();
    }

    private static List<LessonContact> toContactDomains(List<LessonContactEntity> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream()
                .map(e -> LessonContact.builder()
                        .id(e.getId())
                        .type(ContactType.fromCode(e.getType()))
                        .account(e.getAccount())
                        .name(e.getName())
                        .build())
                .toList();
    }

    private static List<LessonNotice> toNoticeDomains(List<LessonNoticeEntity> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream()
                .map(e -> LessonNotice.builder()
                        .id(e.getId())
                        .type(NoticeType.fromCode(e.getType()))
                        .text(e.getText())
                        .build())
                .toList();
    }
}
