package com.latinhouse.api.profile.application.service;

import com.latinhouse.api.profile.application.port.in.GetProfilesAppResponse;
import com.latinhouse.api.profile.application.port.out.FindProfilePort;
import com.latinhouse.api.profile.domain.Profile;
import com.latinhouse.api.profile.domain.Sex;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProfilesServiceTest {

    @InjectMocks
    private GetProfilesService getProfilesService;

    @Mock
    private FindProfilePort findProfilePort;

    @Test
    void getProfiles_noFilter_returnsAllProfiles() {
        Profile p1 = Profile.builder().id("Ab2Cd3Ef").nickname("홍길동").sex(Sex.M).isInstructor(true).build();
        Profile p2 = Profile.builder().id("Zx9Yy8Ww").nickname("김영희").sex(Sex.F).isInstructor(false).build();
        when(findProfilePort.findAll(null)).thenReturn(List.of(p1, p2));

        List<GetProfilesAppResponse> result = getProfilesService.getProfiles(null);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo("Ab2Cd3Ef");
        assertThat(result.get(1).getId()).isEqualTo("Zx9Yy8Ww");
    }

    @Test
    void getProfiles_noFilter_emptyList_returnsEmptyList() {
        when(findProfilePort.findAll(null)).thenReturn(List.of());

        List<GetProfilesAppResponse> result = getProfilesService.getProfiles(null);

        assertThat(result).isEmpty();
    }

    @Test
    void getProfiles_filterInstructor_returnsOnlyInstructors() {
        Profile instructor = Profile.builder().id("Ab2Cd3Ef").nickname("홍길동").sex(Sex.M).isInstructor(true).build();
        when(findProfilePort.findAll(true)).thenReturn(List.of(instructor));

        List<GetProfilesAppResponse> result = getProfilesService.getProfiles(true);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).isInstructor()).isTrue();
    }

    @Test
    void getProfiles_filterNonInstructor_returnsOnlyNonInstructors() {
        Profile nonInstructor = Profile.builder().id("Zx9Yy8Ww").nickname("김영희").sex(Sex.F).isInstructor(false).build();
        when(findProfilePort.findAll(false)).thenReturn(List.of(nonInstructor));

        List<GetProfilesAppResponse> result = getProfilesService.getProfiles(false);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).isInstructor()).isFalse();
    }

    @Test
    void getProfiles_filterInstructor_noInstructors_returnsEmptyList() {
        when(findProfilePort.findAll(true)).thenReturn(List.of());

        List<GetProfilesAppResponse> result = getProfilesService.getProfiles(true);

        assertThat(result).isEmpty();
    }
}
