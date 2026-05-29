package com.latinhouse.api.lesson.application.service;

import com.latinhouse.api.common.exception.LessonValidationException;
import com.latinhouse.api.lesson.application.port.in.CreateLessonAppRequest;
import com.latinhouse.api.lesson.application.port.in.CreateLessonAppResponse;
import com.latinhouse.api.lesson.application.port.out.LoadInstructorPort;
import com.latinhouse.api.lesson.application.port.out.SaveLessonPort;
import com.latinhouse.api.lesson.domain.DiscountType;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.domain.Region;
import com.latinhouse.api.profile.domain.Profile;
import com.latinhouse.api.profile.domain.Sex;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateLessonServiceTest {

    @Mock
    private SaveLessonPort saveLessonPort;

    @Mock
    private LoadInstructorPort loadInstructorPort;

    @InjectMocks
    private CreateLessonService createLessonService;

    private CreateLessonAppRequest.OptionAppReq validOption() {
        return CreateLessonAppRequest.OptionAppReq.builder()
                .startDateTime(LocalDateTime.of(2026, 6, 1, 10, 0))
                .endDateTime(LocalDateTime.of(2026, 6, 1, 12, 0))
                .region(Region.GANGNAM)
                .build();
    }

    private Profile maleInstructor(String id) {
        return Profile.builder().id(id).nickname("남강사").sex(Sex.M).isInstructor(true).build();
    }

    private Profile femaleInstructor(String id) {
        return Profile.builder().id(id).nickname("여강사").sex(Sex.F).isInstructor(true).build();
    }

    @Test
    void createLesson_bothInstructorsNull_throwsWithInstructorLoError() {
        CreateLessonAppRequest request = CreateLessonAppRequest.builder()
                .title("테스트").genre(Genre.SALSA)
                .options(List.of(validOption()))
                .build();

        assertThatThrownBy(() -> createLessonService.createLesson(request))
                .isInstanceOf(LessonValidationException.class)
                .satisfies(e -> assertThat(((LessonValidationException) e).getErrors())
                        .anyMatch(err -> "instructorLo".equals(err.getField())));
    }

    @Test
    void createLesson_instructorLoNotFound_throwsWithError() {
        when(loadInstructorPort.findById("missing")).thenReturn(Optional.empty());

        CreateLessonAppRequest request = CreateLessonAppRequest.builder()
                .title("테스트").genre(Genre.SALSA)
                .instructorLo("missing")
                .options(List.of(validOption()))
                .build();

        assertThatThrownBy(() -> createLessonService.createLesson(request))
                .isInstanceOf(LessonValidationException.class)
                .satisfies(e -> assertThat(((LessonValidationException) e).getErrors())
                        .anyMatch(err -> "instructorLo".equals(err.getField())));
    }

    @Test
    void createLesson_instructorLoNotRegisteredAsInstructor_throwsWithError() {
        Profile notInstructor = Profile.builder()
                .id("inst1").nickname("일반").sex(Sex.M).isInstructor(false).build();
        when(loadInstructorPort.findById("inst1")).thenReturn(Optional.of(notInstructor));

        CreateLessonAppRequest request = CreateLessonAppRequest.builder()
                .title("테스트").genre(Genre.SALSA)
                .instructorLo("inst1")
                .options(List.of(validOption()))
                .build();

        assertThatThrownBy(() -> createLessonService.createLesson(request))
                .isInstanceOf(LessonValidationException.class)
                .satisfies(e -> assertThat(((LessonValidationException) e).getErrors())
                        .anyMatch(err -> "instructorLo".equals(err.getField())));
    }

    @Test
    void createLesson_instructorLoWithFemaleSex_throwsWithError() {
        when(loadInstructorPort.findById("inst1")).thenReturn(Optional.of(femaleInstructor("inst1")));

        CreateLessonAppRequest request = CreateLessonAppRequest.builder()
                .title("테스트").genre(Genre.SALSA)
                .instructorLo("inst1")
                .options(List.of(validOption()))
                .build();

        assertThatThrownBy(() -> createLessonService.createLesson(request))
                .isInstanceOf(LessonValidationException.class)
                .satisfies(e -> assertThat(((LessonValidationException) e).getErrors())
                        .anyMatch(err -> "instructorLo".equals(err.getField())));
    }

    @Test
    void createLesson_instructorLaWithMaleSex_throwsWithError() {
        when(loadInstructorPort.findById("inst2")).thenReturn(Optional.of(maleInstructor("inst2")));

        CreateLessonAppRequest request = CreateLessonAppRequest.builder()
                .title("테스트").genre(Genre.SALSA)
                .instructorLa("inst2")
                .options(List.of(validOption()))
                .build();

        assertThatThrownBy(() -> createLessonService.createLesson(request))
                .isInstanceOf(LessonValidationException.class)
                .satisfies(e -> assertThat(((LessonValidationException) e).getErrors())
                        .anyMatch(err -> "instructorLa".equals(err.getField())));
    }

    @Test
    void createLesson_optionStartNotBeforeEnd_throwsWithError() {
        when(loadInstructorPort.findById("inst1")).thenReturn(Optional.of(maleInstructor("inst1")));

        CreateLessonAppRequest.OptionAppReq badOption = CreateLessonAppRequest.OptionAppReq.builder()
                .startDateTime(LocalDateTime.of(2026, 6, 1, 14, 0))
                .endDateTime(LocalDateTime.of(2026, 6, 1, 12, 0))
                .region(Region.GANGNAM)
                .build();

        CreateLessonAppRequest request = CreateLessonAppRequest.builder()
                .title("테스트").genre(Genre.SALSA)
                .instructorLo("inst1")
                .options(List.of(badOption))
                .build();

        assertThatThrownBy(() -> createLessonService.createLesson(request))
                .isInstanceOf(LessonValidationException.class)
                .satisfies(e -> assertThat(((LessonValidationException) e).getErrors())
                        .anyMatch(err -> err.getField().contains("startDateTime")));
    }

    @Test
    void createLesson_earlybirdDiscountInvalidDate_throwsWithError() {
        when(loadInstructorPort.findById("inst1")).thenReturn(Optional.of(maleInstructor("inst1")));

        CreateLessonAppRequest.DiscountAppReq badDiscount = CreateLessonAppRequest.DiscountAppReq.builder()
                .type(DiscountType.EARLYBIRD)
                .condition("not-a-date")
                .build();

        CreateLessonAppRequest request = CreateLessonAppRequest.builder()
                .title("테스트").genre(Genre.SALSA)
                .instructorLo("inst1")
                .options(List.of(validOption()))
                .discounts(List.of(badDiscount))
                .build();

        assertThatThrownBy(() -> createLessonService.createLesson(request))
                .isInstanceOf(LessonValidationException.class)
                .satisfies(e -> assertThat(((LessonValidationException) e).getErrors())
                        .anyMatch(err -> err.getField().contains("condition")));
    }

    @Test
    void createLesson_sexDiscountInvalidValue_throwsWithError() {
        when(loadInstructorPort.findById("inst1")).thenReturn(Optional.of(maleInstructor("inst1")));

        CreateLessonAppRequest.DiscountAppReq badDiscount = CreateLessonAppRequest.DiscountAppReq.builder()
                .type(DiscountType.SEX)
                .condition("X")
                .build();

        CreateLessonAppRequest request = CreateLessonAppRequest.builder()
                .title("테스트").genre(Genre.SALSA)
                .instructorLo("inst1")
                .options(List.of(validOption()))
                .discounts(List.of(badDiscount))
                .build();

        assertThatThrownBy(() -> createLessonService.createLesson(request))
                .isInstanceOf(LessonValidationException.class)
                .satisfies(e -> assertThat(((LessonValidationException) e).getErrors())
                        .anyMatch(err -> err.getField().contains("condition")));
    }

    @Test
    void createLesson_validRequest_savesAndReturnsId() {
        when(loadInstructorPort.findById("inst1")).thenReturn(Optional.of(maleInstructor("inst1")));
        when(saveLessonPort.save(any())).thenAnswer(inv -> {
            Lesson lesson = inv.getArgument(0);
            return Lesson.builder()
                    .id(1L)
                    .title(lesson.getTitle())
                    .genre(lesson.getGenre())
                    .build();
        });

        CreateLessonAppRequest request = CreateLessonAppRequest.builder()
                .title("테스트 레슨").genre(Genre.SALSA)
                .instructorLo("inst1")
                .options(List.of(validOption()))
                .build();

        CreateLessonAppResponse response = createLessonService.createLesson(request);

        assertThat(response.getId()).isEqualTo(1L);
    }
}
