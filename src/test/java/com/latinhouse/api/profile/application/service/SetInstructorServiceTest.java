package com.latinhouse.api.profile.application.service;

import com.latinhouse.api.common.exception.ProfileNotFoundException;
import com.latinhouse.api.profile.application.port.in.SetInstructorAppRequest;
import com.latinhouse.api.profile.application.port.in.SetInstructorAppResponse;
import com.latinhouse.api.profile.application.port.out.FindProfilePort;
import com.latinhouse.api.profile.application.port.out.UpdateProfilePort;
import com.latinhouse.api.profile.domain.Profile;
import com.latinhouse.api.profile.domain.Sex;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SetInstructorServiceTest {

    @Mock
    private FindProfilePort findProfilePort;

    @Mock
    private UpdateProfilePort updateProfilePort;

    @InjectMocks
    private SetInstructorService setInstructorService;

    private static final Profile EXISTING_PROFILE = Profile.builder()
            .id("Ab2Cd3Ef")
            .nickname("TestUser")
            .sex(Sex.M)
            .isInstructor(false)
            .build();

    @Test
    void setInstructor_existingProfile_returnsIdWith200() {
        Profile updatedProfile = EXISTING_PROFILE.asInstructor();
        when(findProfilePort.findById("Ab2Cd3Ef")).thenReturn(Optional.of(EXISTING_PROFILE));
        when(updateProfilePort.update(any())).thenReturn(updatedProfile);

        SetInstructorAppResponse response = setInstructorService.setInstructor(
                SetInstructorAppRequest.builder().profileId("Ab2Cd3Ef").build());

        assertThat(response.getId()).isEqualTo("Ab2Cd3Ef");
        verify(updateProfilePort).update(any());
    }

    @Test
    void setInstructor_nonExistentProfile_throwsProfileNotFoundException() {
        when(findProfilePort.findById("NOTEXIST")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> setInstructorService.setInstructor(
                SetInstructorAppRequest.builder().profileId("NOTEXIST").build()))
                .isInstanceOf(ProfileNotFoundException.class);
    }

    @Test
    void setInstructor_alreadyInstructor_returnsIdIdempotently() {
        Profile alreadyInstructor = Profile.builder()
                .id("Ab2Cd3Ef")
                .nickname("TestUser")
                .sex(Sex.M)
                .isInstructor(true)
                .build();
        when(findProfilePort.findById("Ab2Cd3Ef")).thenReturn(Optional.of(alreadyInstructor));
        when(updateProfilePort.update(any())).thenReturn(alreadyInstructor);

        SetInstructorAppResponse response = setInstructorService.setInstructor(
                SetInstructorAppRequest.builder().profileId("Ab2Cd3Ef").build());

        assertThat(response.getId()).isEqualTo("Ab2Cd3Ef");
        verify(updateProfilePort).update(any());
    }
}
