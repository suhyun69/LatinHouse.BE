package com.latinhouse.api.lesson.application.service;

import com.latinhouse.api.common.exception.ErrorResponse;
import com.latinhouse.api.common.exception.LessonValidationException;
import com.latinhouse.api.lesson.application.port.in.CreateLessonAppMapper;
import com.latinhouse.api.lesson.application.port.in.CreateLessonAppRequest;
import com.latinhouse.api.lesson.application.port.in.CreateLessonAppResponse;
import com.latinhouse.api.lesson.application.port.in.CreateLessonUseCase;
import com.latinhouse.api.lesson.application.port.out.LoadInstructorPort;
import com.latinhouse.api.lesson.application.port.out.SaveLessonPort;
import com.latinhouse.api.lesson.domain.DiscountType;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.profile.domain.Profile;
import com.latinhouse.api.profile.domain.Sex;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateLessonService implements CreateLessonUseCase {

    private final SaveLessonPort saveLessonPort;
    private final LoadInstructorPort loadInstructorPort;

    @Override
    @Transactional
    public CreateLessonAppResponse createLesson(CreateLessonAppRequest request) {
        List<ErrorResponse.FieldError> errors = new ArrayList<>();

        validateInstructors(request, errors);
        validateOptionDateTimes(request, errors);
        validateDiscountConditions(request, errors);

        if (!errors.isEmpty()) {
            throw new LessonValidationException(errors);
        }

        Lesson lesson = CreateLessonAppMapper.toDomain(request);
        Lesson saved = saveLessonPort.save(lesson);
        return CreateLessonAppMapper.toAppResponse(saved);
    }

    private void validateInstructors(CreateLessonAppRequest request, List<ErrorResponse.FieldError> errors) {
        if (request.getInstructorLo() == null && request.getInstructorLa() == null) {
            errors.add(ErrorResponse.FieldError.builder()
                    .field("instructorLo")
                    .message("남성 강사 또는 여성 강사 중 하나는 반드시 입력해야 합니다.")
                    .build());
            return;
        }

        if (request.getInstructorLo() != null) {
            Optional<Profile> lo = loadInstructorPort.findById(request.getInstructorLo());
            if (lo.isEmpty()) {
                errors.add(ErrorResponse.FieldError.builder()
                        .field("instructorLo")
                        .message("존재하지 않는 강사 ID입니다.")
                        .build());
            } else {
                Profile profile = lo.get();
                if (!profile.isInstructor()) {
                    errors.add(ErrorResponse.FieldError.builder()
                            .field("instructorLo")
                            .message("강사로 등록되지 않은 프로필입니다.")
                            .build());
                } else if (profile.getSex() != Sex.M) {
                    errors.add(ErrorResponse.FieldError.builder()
                            .field("instructorLo")
                            .message("남성 강사(instructorLo)에는 남성(M) 프로필만 등록 가능합니다.")
                            .build());
                }
            }
        }

        if (request.getInstructorLa() != null) {
            Optional<Profile> la = loadInstructorPort.findById(request.getInstructorLa());
            if (la.isEmpty()) {
                errors.add(ErrorResponse.FieldError.builder()
                        .field("instructorLa")
                        .message("존재하지 않는 강사 ID입니다.")
                        .build());
            } else {
                Profile profile = la.get();
                if (!profile.isInstructor()) {
                    errors.add(ErrorResponse.FieldError.builder()
                            .field("instructorLa")
                            .message("강사로 등록되지 않은 프로필입니다.")
                            .build());
                } else if (profile.getSex() != Sex.F) {
                    errors.add(ErrorResponse.FieldError.builder()
                            .field("instructorLa")
                            .message("여성 강사(instructorLa)에는 여성(F) 프로필만 등록 가능합니다.")
                            .build());
                }
            }
        }
    }

    private void validateOptionDateTimes(CreateLessonAppRequest request, List<ErrorResponse.FieldError> errors) {
        if (request.getOptions() == null) return;
        for (int i = 0; i < request.getOptions().size(); i++) {
            CreateLessonAppRequest.OptionAppReq opt = request.getOptions().get(i);
            if (opt.getStartDateTime() != null && opt.getEndDateTime() != null
                    && !opt.getStartDateTime().isBefore(opt.getEndDateTime())) {
                errors.add(ErrorResponse.FieldError.builder()
                        .field("options[" + i + "].startDateTime")
                        .message("시작 일시는 종료 일시보다 이전이어야 합니다.")
                        .build());
            }
        }
    }

    private void validateDiscountConditions(CreateLessonAppRequest request, List<ErrorResponse.FieldError> errors) {
        if (request.getDiscounts() == null) return;
        for (int i = 0; i < request.getDiscounts().size(); i++) {
            CreateLessonAppRequest.DiscountAppReq discount = request.getDiscounts().get(i);
            if (discount.getType() == null || discount.getCondition() == null) continue;

            if (discount.getType() == DiscountType.EARLYBIRD) {
                if (!isValidDate(discount.getCondition())) {
                    errors.add(ErrorResponse.FieldError.builder()
                            .field("discounts[" + i + "].condition")
                            .message("얼리버드 할인 조건은 yyyy-MM-dd 형식의 날짜여야 합니다.")
                            .build());
                }
            } else if (discount.getType() == DiscountType.SEX) {
                if (!"M".equals(discount.getCondition()) && !"F".equals(discount.getCondition())) {
                    errors.add(ErrorResponse.FieldError.builder()
                            .field("discounts[" + i + "].condition")
                            .message("성별 할인 조건은 M 또는 F만 입력 가능합니다.")
                            .build());
                }
            }
        }
    }

    private boolean isValidDate(String value) {
        try {
            LocalDate.parse(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
