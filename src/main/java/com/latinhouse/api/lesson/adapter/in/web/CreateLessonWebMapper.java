package com.latinhouse.api.lesson.adapter.in.web;

import com.latinhouse.api.lesson.application.port.in.CreateLessonAppRequest;
import com.latinhouse.api.lesson.application.port.in.CreateLessonAppResponse;
import com.latinhouse.api.lesson.domain.ContactType;
import com.latinhouse.api.lesson.domain.DiscountType;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.NoticeType;
import com.latinhouse.api.lesson.domain.Region;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class CreateLessonWebMapper {

    private CreateLessonWebMapper() {}

    public static CreateLessonAppRequest toAppRequest(CreateLessonWebRequest web) {
        return CreateLessonAppRequest.builder()
                .title(web.getTitle())
                .genre(Genre.fromCode(web.getGenre()))
                .instructorLo(web.getInstructorLo())
                .instructorLa(web.getInstructorLa())
                .options(toOptionAppReqs(web.getOptions()))
                .amount(web.getAmount())
                .discounts(toDiscountAppReqs(web.getDiscounts()))
                .account(toAccountAppReq(web.getAccount()))
                .contacts(toContactAppReqs(web.getContacts()))
                .isActive(web.getIsActive())
                .notices(toNoticeAppReqs(web.getNotices()))
                .build();
    }

    public static CreateLessonWebResponse toWebResponse(CreateLessonAppResponse appResponse) {
        return CreateLessonWebResponse.builder()
                .id(appResponse.getId())
                .build();
    }

    private static List<CreateLessonAppRequest.OptionAppReq> toOptionAppReqs(
            List<CreateLessonWebRequest.OptionWebReq> webs) {
        if (webs == null) return Collections.emptyList();
        return webs.stream()
                .map(w -> CreateLessonAppRequest.OptionAppReq.builder()
                        .startDateTime(LocalDateTime.parse(w.getStartDate() + "T" + w.getStartTime()))
                        .endDateTime(LocalDateTime.parse(w.getEndDate() + "T" + w.getEndTime()))
                        .region(Region.fromCode(w.getRegion()))
                        .place(w.getPlace())
                        .placeUrl(w.getPlaceUrl())
                        .build())
                .toList();
    }

    private static List<CreateLessonAppRequest.DiscountAppReq> toDiscountAppReqs(
            List<CreateLessonWebRequest.DiscountWebReq> webs) {
        if (webs == null) return Collections.emptyList();
        return webs.stream()
                .map(w -> CreateLessonAppRequest.DiscountAppReq.builder()
                        .type(w.getType() != null ? DiscountType.fromCode(w.getType()) : null)
                        .condition(w.getCondition())
                        .amount(w.getAmount())
                        .build())
                .toList();
    }

    private static CreateLessonAppRequest.AccountAppReq toAccountAppReq(
            CreateLessonWebRequest.AccountWebReq web) {
        if (web == null) return null;
        return CreateLessonAppRequest.AccountAppReq.builder()
                .bank(web.getBank())
                .account(web.getAccount())
                .name(web.getName())
                .build();
    }

    private static List<CreateLessonAppRequest.ContactAppReq> toContactAppReqs(
            List<CreateLessonWebRequest.ContactWebReq> webs) {
        if (webs == null) return Collections.emptyList();
        return webs.stream()
                .map(w -> CreateLessonAppRequest.ContactAppReq.builder()
                        .type(w.getType() != null ? ContactType.fromCode(w.getType()) : null)
                        .account(w.getAccount())
                        .name(w.getName())
                        .build())
                .toList();
    }

    private static List<CreateLessonAppRequest.NoticeAppReq> toNoticeAppReqs(
            List<CreateLessonWebRequest.NoticeWebReq> webs) {
        if (webs == null) return Collections.emptyList();
        return webs.stream()
                .map(w -> CreateLessonAppRequest.NoticeAppReq.builder()
                        .type(w.getType() != null ? NoticeType.fromCode(w.getType()) : null)
                        .text(w.getText())
                        .build())
                .toList();
    }
}
