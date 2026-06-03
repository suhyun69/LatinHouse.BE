package com.latinhouse.api.lesson.application.service;

import com.latinhouse.api.lesson.application.port.in.CreateLessonAppResponse;
import com.latinhouse.api.lesson.application.port.in.CreateLessonUseCase;
import com.latinhouse.api.lesson.application.port.in.CreateRandomLessonAppResponse;
import com.latinhouse.api.profile.application.port.in.CreateProfileAppRequest;
import com.latinhouse.api.profile.application.port.in.CreateProfileAppResponse;
import com.latinhouse.api.profile.application.port.in.CreateProfileUseCase;
import com.latinhouse.api.profile.application.port.in.GetProfilesAppResponse;
import com.latinhouse.api.profile.application.port.in.GetProfilesUseCase;
import com.latinhouse.api.profile.application.port.in.SetInstructorAppRequest;
import com.latinhouse.api.profile.application.port.in.SetInstructorAppResponse;
import com.latinhouse.api.profile.application.port.in.SetInstructorUseCase;
import com.latinhouse.api.profile.domain.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateRandomLessonServiceTest {

    @Mock
    private GetProfilesUseCase getProfilesUseCase;
    @Mock
    private CreateProfileUseCase createProfileUseCase;
    @Mock
    private SetInstructorUseCase setInstructorUseCase;
    @Mock
    private CreateLessonUseCase createLessonUseCase;

    private CreateRandomLessonService service;

    @BeforeEach
    void setUp() {
        service = new CreateRandomLessonService(
                getProfilesUseCase, createProfileUseCase, setInstructorUseCase, createLessonUseCase);
    }

    @Test
    void createRandomLesson_withExistingBothInstructors_doesNotCreateProfile() {
        GetProfilesAppResponse maleInstructor = GetProfilesAppResponse.builder()
                .id("maleId").nickname("남강사").sex(Sex.M).isInstructor(true).build();
        GetProfilesAppResponse femaleInstructor = GetProfilesAppResponse.builder()
                .id("femaleId").nickname("여강사").sex(Sex.F).isInstructor(true).build();

        when(getProfilesUseCase.getProfiles(true)).thenReturn(List.of(maleInstructor, femaleInstructor));
        when(createLessonUseCase.createLesson(any()))
                .thenReturn(CreateLessonAppResponse.builder().id(1L).build());

        CreateRandomLessonAppResponse response = service.createRandomLesson();

        assertThat(response.getId()).isEqualTo(1L);
        verify(createProfileUseCase, never()).createProfile(any());
        verify(setInstructorUseCase, never()).setInstructor(any());
    }

    @Test
    void createRandomLesson_withNoInstructors_createsBothProfiles() {
        when(getProfilesUseCase.getProfiles(true)).thenReturn(List.of());
        when(createProfileUseCase.createProfile(any()))
                .thenReturn(CreateProfileAppResponse.builder().id("newId1").build())
                .thenReturn(CreateProfileAppResponse.builder().id("newId2").build());
        when(setInstructorUseCase.setInstructor(any()))
                .thenReturn(SetInstructorAppResponse.builder().id("newId1").build())
                .thenReturn(SetInstructorAppResponse.builder().id("newId2").build());
        when(createLessonUseCase.createLesson(any()))
                .thenReturn(CreateLessonAppResponse.builder().id(2L).build());

        CreateRandomLessonAppResponse response = service.createRandomLesson();

        assertThat(response.getId()).isEqualTo(2L);
        verify(createProfileUseCase, times(2)).createProfile(any());
        verify(setInstructorUseCase, times(2)).setInstructor(any());
    }

    @Test
    void createRandomLesson_withOnlyMaleInstructor_createsOnlyFemaleProfile() {
        GetProfilesAppResponse maleInstructor = GetProfilesAppResponse.builder()
                .id("maleId").nickname("남강사").sex(Sex.M).isInstructor(true).build();

        when(getProfilesUseCase.getProfiles(true)).thenReturn(List.of(maleInstructor));
        when(createProfileUseCase.createProfile(any()))
                .thenReturn(CreateProfileAppResponse.builder().id("newFemaleId").build());
        when(setInstructorUseCase.setInstructor(any()))
                .thenReturn(SetInstructorAppResponse.builder().id("newFemaleId").build());
        when(createLessonUseCase.createLesson(any()))
                .thenReturn(CreateLessonAppResponse.builder().id(3L).build());

        service.createRandomLesson();

        ArgumentCaptor<CreateProfileAppRequest> captor = ArgumentCaptor.forClass(CreateProfileAppRequest.class);
        verify(createProfileUseCase, times(1)).createProfile(captor.capture());
        assertThat(captor.getValue().getSex()).isEqualTo(Sex.F);
    }

    @Test
    void createRandomLesson_whenCreateProfileThrows_propagatesException() {
        when(getProfilesUseCase.getProfiles(true)).thenReturn(List.of());
        when(createProfileUseCase.createProfile(any()))
                .thenThrow(new RuntimeException("강사 프로필 생성 실패"));

        assertThatThrownBy(() -> service.createRandomLesson())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("강사 프로필 생성 실패");
    }
}
