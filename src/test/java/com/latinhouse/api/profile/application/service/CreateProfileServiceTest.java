package com.latinhouse.api.profile.application.service;

import com.latinhouse.api.profile.application.port.in.CreateProfileAppRequest;
import com.latinhouse.api.profile.application.port.in.CreateProfileAppResponse;
import com.latinhouse.api.profile.application.port.out.SaveProfilePort;
import com.latinhouse.api.profile.domain.Profile;
import com.latinhouse.api.profile.domain.Sex;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProfileServiceTest {

    @Mock
    private SaveProfilePort saveProfilePort;

    @InjectMocks
    private CreateProfileService createProfileService;

    @Test
    void createProfile_generatesIdOfLength8AndSaves() {
        when(saveProfilePort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        CreateProfileAppRequest request = CreateProfileAppRequest.builder()
                .nickname("TestUser")
                .sex(Sex.M)
                .build();

        CreateProfileAppResponse response = createProfileService.createProfile(request);

        assertThat(response.getId()).hasSize(8);
        assertThat(response.getId()).matches("[A-HJ-NP-Za-hj-km-np-z2-9]{8}");
        verify(saveProfilePort, times(1)).save(any(Profile.class));
    }

    @Test
    void createProfile_setsIsInstructorFalse() {
        when(saveProfilePort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        CreateProfileAppRequest request = CreateProfileAppRequest.builder()
                .nickname("Instructor")
                .sex(Sex.F)
                .build();

        createProfileService.createProfile(request);

        verify(saveProfilePort).save(argThat(profile -> !profile.isInstructor()));
    }
}
