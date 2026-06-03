package com.latinhouse.api.lesson.adapter.in.web;

import com.latinhouse.api.common.exception.InvalidParamException;
import com.latinhouse.api.lesson.application.port.in.GetLessonsAppRequest;
import com.latinhouse.api.lesson.application.port.in.GetLessonsAppResponse;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.Region;

import java.util.List;

public class GetLessonsWebMapper {

    private GetLessonsWebMapper() {}

    public static GetLessonsAppRequest toAppRequest(String region, String instructor, String genre) {
        return GetLessonsAppRequest.builder()
                .region(parseRegion(region))
                .instructor(instructor)
                .genre(parseGenre(genre))
                .build();
    }

    public static List<GetLessonsWebResponse> toWebResponseList(List<GetLessonsAppResponse> appResponses) {
        return appResponses.stream()
                .map(GetLessonsWebMapper::toWebResponse)
                .toList();
    }

    private static GetLessonsWebResponse toWebResponse(GetLessonsAppResponse app) {
        return GetLessonsWebResponse.builder()
                .optionId(app.getOptionId())
                .lessonNo(app.getLessonNo())
                .instructorLo(app.getInstructorLo())
                .instructorLa(app.getInstructorLa())
                .title(app.getTitle())
                .genre(app.getGenre())
                .startDate(app.getStartDate())
                .startTime(app.getStartTime())
                .endDate(app.getEndDate())
                .endTime(app.getEndTime())
                .region(app.getRegion())
                .price(app.getPrice())
                .discountCondition(app.getDiscountCondition())
                .discountAmount(app.getDiscountAmount())
                .status(app.getStatus())
                .build();
    }

    private static Region parseRegion(String region) {
        if (region == null) return null;
        try {
            return Region.fromCode(region);
        } catch (IllegalArgumentException e) {
            throw new InvalidParamException("region", "지역은 GN 또는 HD만 입력 가능합니다.");
        }
    }

    private static Genre parseGenre(String genre) {
        if (genre == null) return null;
        try {
            return Genre.fromCode(genre);
        } catch (IllegalArgumentException e) {
            throw new InvalidParamException("genre", "장르는 S 또는 B만 입력 가능합니다.");
        }
    }
}
