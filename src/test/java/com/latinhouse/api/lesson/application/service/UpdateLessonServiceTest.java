package com.latinhouse.api.lesson.application.service;

import com.latinhouse.api.common.exception.LessonNotFoundException;
import com.latinhouse.api.common.exception.LessonValidationException;
import com.latinhouse.api.lesson.application.port.in.CreateLessonAppRequest;
import com.latinhouse.api.lesson.application.port.in.UpdateLessonAppRequest;
import com.latinhouse.api.lesson.application.port.in.UpdateLessonAppResponse;
import com.latinhouse.api.lesson.application.port.out.LoadInstructorPort;
import com.latinhouse.api.lesson.application.port.out.LoadLessonPort;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateLessonServiceTest {

    @Mock
    private LoadLessonPort loadLessonPort;

    @Mock
    private SaveLessonPort saveLessonPort;

    @Mock
    private LoadInstructorPort loadInstructorPort;

    @InjectMocks
    private UpdateLessonService updateLessonService;

    private static final LocalDateTime START = LocalDateTime.of(2026, 7, 1, 19, 0);
    private static final LocalDateTime END = LocalDateTime.of(2026, 7, 1, 21, 0);

    private UpdateLessonAppRequest buildValidRequest(Long lessonNo) {
        return UpdateLessonAppRequest.builder()
                .lessonNo(lessonNo)
                .title("살사 중급반")
                .genre(Genre.SALSA)
                .instructorLo("loId")
                .options(List.of(CreateLessonAppRequest.OptionAppReq.builder()
                        .startDateTime(START).endDateTime(END).region(Region.GANGNAM)
                        .build()))
                .build();
    }

    private Lesson buildSavedLesson(Long id) {
        return Lesson.builder().id(id).title("살사 중급반").genre(Genre.SALSA)
                .instructorLo("loId").options(List.of()).discounts(List.of())
                .contacts(List.of()).notices(List.of()).isActive(true).build();
    }

    @Test
    void updateLesson_validRequest_returnsResponse() {
        Lesson saved = buildSavedLesson(1L);
        when(loadLessonPort.loadLesson(1L)).thenReturn(saved);
        when(loadInstructorPort.findById("loId")).thenReturn(Optional.of(
                Profile.builder().id("loId").sex(Sex.M).isInstructor(true).build()));
        when(saveLessonPort.save(any())).thenReturn(saved);

        UpdateLessonAppResponse response = updateLessonService.updateLesson(buildValidRequest(1L));

        assertThat(response.getId()).isEqualTo(1L);
        verify(loadLessonPort).loadLesson(1L);
        verify(saveLessonPort).save(any());
    }

    @Test
    void updateLesson_lessonNotFound_throwsLessonNotFoundException() {
        when(loadLessonPort.loadLesson(9999L)).thenThrow(new LessonNotFoundException(9999L));

        assertThatThrownBy(() -> updateLessonService.updateLesson(buildValidRequest(9999L)))
                .isInstanceOf(LessonNotFoundException.class);
    }

    @Test
    void updateLesson_bothInstructorsNull_throwsValidationException() {
        Lesson saved = buildSavedLesson(1L);
        when(loadLessonPort.loadLesson(1L)).thenReturn(saved);

        UpdateLessonAppRequest request = UpdateLessonAppRequest.builder()
                .lessonNo(1L).title("살사 중급반").genre(Genre.SALSA)
                .instructorLo(null).instructorLa(null)
                .options(List.of(CreateLessonAppRequest.OptionAppReq.builder()
                        .startDateTime(START).endDateTime(END).region(Region.GANGNAM).build()))
                .build();

        assertThatThrownBy(() -> updateLessonService.updateLesson(request))
                .isInstanceOf(LessonValidationException.class);
    }

    @Test
    void updateLesson_instructorLoSexMismatch_throwsValidationException() {
        Lesson saved = buildSavedLesson(1L);
        when(loadLessonPort.loadLesson(1L)).thenReturn(saved);
        when(loadInstructorPort.findById("femaleId")).thenReturn(Optional.of(
                Profile.builder().id("femaleId").sex(Sex.F).isInstructor(true).build()));

        UpdateLessonAppRequest request = UpdateLessonAppRequest.builder()
                .lessonNo(1L).title("살사 중급반").genre(Genre.SALSA)
                .instructorLo("femaleId")
                .options(List.of(CreateLessonAppRequest.OptionAppReq.builder()
                        .startDateTime(START).endDateTime(END).region(Region.GANGNAM).build()))
                .build();

        assertThatThrownBy(() -> updateLessonService.updateLesson(request))
                .isInstanceOf(LessonValidationException.class);
    }

    @Test
    void updateLesson_endDateTimeBeforeStart_throwsValidationException() {
        Lesson saved = buildSavedLesson(1L);
        when(loadLessonPort.loadLesson(1L)).thenReturn(saved);
        when(loadInstructorPort.findById("loId")).thenReturn(Optional.of(
                Profile.builder().id("loId").sex(Sex.M).isInstructor(true).build()));

        UpdateLessonAppRequest request = UpdateLessonAppRequest.builder()
                .lessonNo(1L).title("살사 중급반").genre(Genre.SALSA)
                .instructorLo("loId")
                .options(List.of(CreateLessonAppRequest.OptionAppReq.builder()
                        .startDateTime(END).endDateTime(START).region(Region.GANGNAM).build()))
                .build();

        assertThatThrownBy(() -> updateLessonService.updateLesson(request))
                .isInstanceOf(LessonValidationException.class);
    }

    @Test
    void updateLesson_earlybirdDiscountInvalidCondition_throwsValidationException() {
        Lesson saved = buildSavedLesson(1L);
        when(loadLessonPort.loadLesson(1L)).thenReturn(saved);
        when(loadInstructorPort.findById("loId")).thenReturn(Optional.of(
                Profile.builder().id("loId").sex(Sex.M).isInstructor(true).build()));

        UpdateLessonAppRequest request = UpdateLessonAppRequest.builder()
                .lessonNo(1L).title("살사 중급반").genre(Genre.SALSA)
                .instructorLo("loId")
                .options(List.of(CreateLessonAppRequest.OptionAppReq.builder()
                        .startDateTime(START).endDateTime(END).region(Region.GANGNAM).build()))
                .discounts(List.of(CreateLessonAppRequest.DiscountAppReq.builder()
                        .type(DiscountType.EARLYBIRD).condition("not-a-date").build()))
                .build();

        assertThatThrownBy(() -> updateLessonService.updateLesson(request))
                .isInstanceOf(LessonValidationException.class);
    }
}
