package com.latinhouse.api.lesson.application.port.in;

import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.domain.LessonAccount;
import com.latinhouse.api.lesson.domain.LessonContact;
import com.latinhouse.api.lesson.domain.LessonDiscount;
import com.latinhouse.api.lesson.domain.LessonNotice;
import com.latinhouse.api.lesson.domain.LessonOption;

import java.util.Collections;
import java.util.List;

public class GetLessonAppMapper {

    private GetLessonAppMapper() {}

    public static GetLessonAppResponse toAppResponse(Lesson lesson) {
        return GetLessonAppResponse.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .genre(lesson.getGenre())
                .instructorLo(lesson.getInstructorLo())
                .instructorLa(lesson.getInstructorLa())
                .options(toOptionResponses(lesson.getOptions()))
                .amount(lesson.getAmount())
                .discounts(toDiscountResponses(lesson.getDiscounts()))
                .account(toAccountResponse(lesson.getAccount()))
                .contacts(toContactResponses(lesson.getContacts()))
                .isActive(lesson.isActive())
                .notices(toNoticeResponses(lesson.getNotices()))
                .build();
    }

    private static List<GetLessonAppResponse.OptionResponse> toOptionResponses(List<LessonOption> options) {
        if (options == null) return Collections.emptyList();
        return options.stream()
                .map(o -> GetLessonAppResponse.OptionResponse.builder()
                        .id(o.getId())
                        .startDateTime(o.getStartDateTime())
                        .endDateTime(o.getEndDateTime())
                        .region(o.getRegion())
                        .place(o.getPlace())
                        .placeUrl(o.getPlaceUrl())
                        .build())
                .toList();
    }

    private static List<GetLessonAppResponse.DiscountResponse> toDiscountResponses(List<LessonDiscount> discounts) {
        if (discounts == null) return Collections.emptyList();
        return discounts.stream()
                .map(d -> GetLessonAppResponse.DiscountResponse.builder()
                        .id(d.getId())
                        .type(d.getType())
                        .condition(d.getCondition())
                        .amount(d.getAmount())
                        .build())
                .toList();
    }

    private static GetLessonAppResponse.AccountResponse toAccountResponse(LessonAccount account) {
        if (account == null) return null;
        return GetLessonAppResponse.AccountResponse.builder()
                .id(account.getId())
                .bank(account.getBank())
                .account(account.getAccount())
                .name(account.getName())
                .build();
    }

    private static List<GetLessonAppResponse.ContactResponse> toContactResponses(List<LessonContact> contacts) {
        if (contacts == null) return Collections.emptyList();
        return contacts.stream()
                .map(c -> GetLessonAppResponse.ContactResponse.builder()
                        .id(c.getId())
                        .type(c.getType())
                        .account(c.getAccount())
                        .name(c.getName())
                        .build())
                .toList();
    }

    private static List<GetLessonAppResponse.NoticeResponse> toNoticeResponses(List<LessonNotice> notices) {
        if (notices == null) return Collections.emptyList();
        return notices.stream()
                .map(n -> GetLessonAppResponse.NoticeResponse.builder()
                        .id(n.getId())
                        .type(n.getType())
                        .text(n.getText())
                        .build())
                .toList();
    }
}
