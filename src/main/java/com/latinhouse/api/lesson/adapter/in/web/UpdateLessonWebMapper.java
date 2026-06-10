package com.latinhouse.api.lesson.adapter.in.web;

import com.latinhouse.api.lesson.application.port.in.UpdateLessonAppRequest;
import com.latinhouse.api.lesson.application.port.in.UpdateLessonAppResponse;
import com.latinhouse.api.lesson.domain.ContactType;
import com.latinhouse.api.lesson.domain.DiscountType;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.NoticeType;
import com.latinhouse.api.lesson.domain.Region;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class UpdateLessonWebMapper {

    private UpdateLessonWebMapper() {}

    public static UpdateLessonAppRequest toAppRequest(Long lessonNo, UpdateLessonWebRequest web) {
        return UpdateLessonAppRequest.builder()
                .lessonNo(lessonNo)
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

    public static UpdateLessonWebResponse toWebResponse(UpdateLessonAppResponse appResponse) {
        return UpdateLessonWebResponse.builder()
                .id(appResponse.getId())
                .build();
    }

    private static List<UpdateLessonAppRequest.OptionAppReq> toOptionAppReqs(
            List<UpdateLessonWebRequest.OptionWebReq> webs) {
        if (webs == null) return Collections.emptyList();
        return webs.stream()
                .map(w -> UpdateLessonAppRequest.OptionAppReq.builder()
                        .id(w.getId())
                        .startDateTime(LocalDateTime.parse(w.getStartDate() + "T" + w.getStartTime()))
                        .endDateTime(LocalDateTime.parse(w.getEndDate() + "T" + w.getEndTime()))
                        .region(Region.fromCode(w.getRegion()))
                        .place(w.getPlace())
                        .placeUrl(w.getPlaceUrl())
                        .build())
                .toList();
    }

    private static List<UpdateLessonAppRequest.DiscountAppReq> toDiscountAppReqs(
            List<UpdateLessonWebRequest.DiscountWebReq> webs) {
        if (webs == null) return Collections.emptyList();
        return webs.stream()
                .map(w -> UpdateLessonAppRequest.DiscountAppReq.builder()
                        .id(w.getId())
                        .type(w.getType() != null ? DiscountType.fromCode(w.getType()) : null)
                        .condition(w.getCondition())
                        .amount(w.getAmount())
                        .build())
                .toList();
    }

    private static UpdateLessonAppRequest.AccountAppReq toAccountAppReq(
            UpdateLessonWebRequest.AccountWebReq web) {
        if (web == null) return null;
        return UpdateLessonAppRequest.AccountAppReq.builder()
                .id(web.getId())
                .bank(web.getBank())
                .account(web.getAccount())
                .name(web.getName())
                .build();
    }

    private static List<UpdateLessonAppRequest.ContactAppReq> toContactAppReqs(
            List<UpdateLessonWebRequest.ContactWebReq> webs) {
        if (webs == null) return Collections.emptyList();
        return webs.stream()
                .map(w -> UpdateLessonAppRequest.ContactAppReq.builder()
                        .id(w.getId())
                        .type(w.getType() != null ? ContactType.fromCode(w.getType()) : null)
                        .account(w.getAccount())
                        .name(w.getName())
                        .build())
                .toList();
    }

    private static List<UpdateLessonAppRequest.NoticeAppReq> toNoticeAppReqs(
            List<UpdateLessonWebRequest.NoticeWebReq> webs) {
        if (webs == null) return Collections.emptyList();
        return webs.stream()
                .map(w -> UpdateLessonAppRequest.NoticeAppReq.builder()
                        .id(w.getId())
                        .type(w.getType() != null ? NoticeType.fromCode(w.getType()) : null)
                        .text(w.getText())
                        .build())
                .toList();
    }
}
