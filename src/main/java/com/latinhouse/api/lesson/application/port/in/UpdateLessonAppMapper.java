package com.latinhouse.api.lesson.application.port.in;

import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.domain.LessonAccount;
import com.latinhouse.api.lesson.domain.LessonContact;
import com.latinhouse.api.lesson.domain.LessonDiscount;
import com.latinhouse.api.lesson.domain.LessonNotice;
import com.latinhouse.api.lesson.domain.LessonOption;

import java.util.Collections;
import java.util.List;

public class UpdateLessonAppMapper {

    private UpdateLessonAppMapper() {}

    public static Lesson toDomain(UpdateLessonAppRequest request) {
        return Lesson.builder()
                .id(request.getLessonNo())
                .title(request.getTitle())
                .genre(request.getGenre())
                .instructorLo(request.getInstructorLo())
                .instructorLa(request.getInstructorLa())
                .options(toOptionDomains(request.getOptions()))
                .amount(request.getAmount())
                .discounts(toDiscountDomains(request.getDiscounts()))
                .account(toAccountDomain(request.getAccount()))
                .contacts(toContactDomains(request.getContacts()))
                .isActive(request.getIsActive() == null || request.getIsActive())
                .notices(toNoticeDomains(request.getNotices()))
                .build();
    }

    public static UpdateLessonAppResponse toAppResponse(Lesson lesson) {
        return UpdateLessonAppResponse.builder()
                .id(lesson.getId())
                .build();
    }

    private static List<LessonOption> toOptionDomains(List<UpdateLessonAppRequest.OptionAppReq> reqs) {
        if (reqs == null) return Collections.emptyList();
        return reqs.stream()
                .map(r -> LessonOption.builder()
                        .id(r.getId())
                        .startDateTime(r.getStartDateTime())
                        .endDateTime(r.getEndDateTime())
                        .region(r.getRegion())
                        .place(r.getPlace())
                        .placeUrl(r.getPlaceUrl())
                        .build())
                .toList();
    }

    private static List<LessonDiscount> toDiscountDomains(List<UpdateLessonAppRequest.DiscountAppReq> reqs) {
        if (reqs == null) return Collections.emptyList();
        return reqs.stream()
                .map(r -> LessonDiscount.builder()
                        .id(r.getId())
                        .type(r.getType())
                        .condition(r.getCondition())
                        .amount(r.getAmount())
                        .build())
                .toList();
    }

    private static LessonAccount toAccountDomain(UpdateLessonAppRequest.AccountAppReq req) {
        if (req == null) return null;
        return LessonAccount.builder()
                .id(req.getId())
                .bank(req.getBank())
                .account(req.getAccount())
                .name(req.getName())
                .build();
    }

    private static List<LessonContact> toContactDomains(List<UpdateLessonAppRequest.ContactAppReq> reqs) {
        if (reqs == null) return Collections.emptyList();
        return reqs.stream()
                .map(r -> LessonContact.builder()
                        .id(r.getId())
                        .type(r.getType())
                        .account(r.getAccount())
                        .name(r.getName())
                        .build())
                .toList();
    }

    private static List<LessonNotice> toNoticeDomains(List<UpdateLessonAppRequest.NoticeAppReq> reqs) {
        if (reqs == null) return Collections.emptyList();
        return reqs.stream()
                .map(r -> LessonNotice.builder()
                        .id(r.getId())
                        .type(r.getType())
                        .text(r.getText())
                        .build())
                .toList();
    }
}
