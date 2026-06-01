package com.latinhouse.api.lesson.adapter.in.web;

import com.latinhouse.api.lesson.application.port.in.GetLessonAppResponse;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class GetLessonWebMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private GetLessonWebMapper() {}

    public static GetLessonWebResponse toWebResponse(GetLessonAppResponse appResponse) {
        return GetLessonWebResponse.builder()
                .id(appResponse.getId())
                .title(appResponse.getTitle())
                .genre(appResponse.getGenre().getCode())
                .instructorLo(appResponse.getInstructorLo())
                .instructorLa(appResponse.getInstructorLa())
                .options(toOptionResponses(appResponse.getOptions()))
                .amount(appResponse.getAmount())
                .discounts(toDiscountResponses(appResponse.getDiscounts()))
                .account(toAccountResponse(appResponse.getAccount()))
                .contacts(toContactResponses(appResponse.getContacts()))
                .isActive(appResponse.isActive())
                .notices(toNoticeResponses(appResponse.getNotices()))
                .build();
    }

    private static List<GetLessonWebResponse.OptionResponse> toOptionResponses(
            List<GetLessonAppResponse.OptionResponse> options) {
        if (options == null) return Collections.emptyList();
        return options.stream()
                .map(o -> GetLessonWebResponse.OptionResponse.builder()
                        .id(o.getId())
                        .startDate(o.getStartDateTime().format(DATE_FORMATTER))
                        .startTime(o.getStartDateTime().format(TIME_FORMATTER))
                        .endDate(o.getEndDateTime().format(DATE_FORMATTER))
                        .endTime(o.getEndDateTime().format(TIME_FORMATTER))
                        .region(o.getRegion().getCode())
                        .place(o.getPlace())
                        .placeUrl(o.getPlaceUrl())
                        .build())
                .toList();
    }

    private static List<GetLessonWebResponse.DiscountResponse> toDiscountResponses(
            List<GetLessonAppResponse.DiscountResponse> discounts) {
        if (discounts == null) return Collections.emptyList();
        return discounts.stream()
                .map(d -> GetLessonWebResponse.DiscountResponse.builder()
                        .id(d.getId())
                        .type(d.getType().getCode())
                        .condition(d.getCondition())
                        .amount(d.getAmount())
                        .build())
                .toList();
    }

    private static GetLessonWebResponse.AccountResponse toAccountResponse(
            GetLessonAppResponse.AccountResponse account) {
        if (account == null) return null;
        return GetLessonWebResponse.AccountResponse.builder()
                .id(account.getId())
                .bank(account.getBank())
                .account(account.getAccount())
                .name(account.getName())
                .build();
    }

    private static List<GetLessonWebResponse.ContactResponse> toContactResponses(
            List<GetLessonAppResponse.ContactResponse> contacts) {
        if (contacts == null) return Collections.emptyList();
        return contacts.stream()
                .map(c -> GetLessonWebResponse.ContactResponse.builder()
                        .id(c.getId())
                        .type(c.getType().getCode())
                        .account(c.getAccount())
                        .name(c.getName())
                        .build())
                .toList();
    }

    private static List<GetLessonWebResponse.NoticeResponse> toNoticeResponses(
            List<GetLessonAppResponse.NoticeResponse> notices) {
        if (notices == null) return Collections.emptyList();
        return notices.stream()
                .map(n -> GetLessonWebResponse.NoticeResponse.builder()
                        .id(n.getId())
                        .type(n.getType().getCode())
                        .text(n.getText())
                        .build())
                .toList();
    }
}
