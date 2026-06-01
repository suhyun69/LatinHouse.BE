package com.latinhouse.api.profile.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.latinhouse.api.common.config.SecurityConfig;
import com.latinhouse.api.common.exception.GlobalExceptionHandler;
import com.latinhouse.api.common.exception.ProfileNotFoundException;
import com.latinhouse.api.profile.application.port.in.CreateProfileAppResponse;
import com.latinhouse.api.profile.application.port.in.CreateProfileUseCase;
import com.latinhouse.api.profile.application.port.in.GetProfilesAppResponse;
import com.latinhouse.api.profile.application.port.in.GetProfilesUseCase;
import com.latinhouse.api.profile.application.port.in.SetInstructorAppResponse;
import com.latinhouse.api.profile.application.port.in.SetInstructorUseCase;
import com.latinhouse.api.profile.domain.Sex;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
@Import({GlobalExceptionHandler.class, SecurityConfig.class})
class ProfileControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateProfileUseCase createProfileUseCase;

    @MockitoBean
    private SetInstructorUseCase setInstructorUseCase;

    @MockitoBean
    private GetProfilesUseCase getProfilesUseCase;

    @Test
    void createProfile_validRequest_returns201WithId() throws Exception {
        when(createProfileUseCase.createProfile(any()))
                .thenReturn(CreateProfileAppResponse.builder().id("Ab2Cd3Ef").build());

        mockMvc.perform(post("/api/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(Map.of("nickname", "TestUser", "sex", "M"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("Ab2Cd3Ef"));
    }

    @Test
    void createProfile_missingNickname_returns400WithMessage() throws Exception {
        mockMvc.perform(post("/api/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(Map.of("sex", "M"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors[0].field").value("nickname"))
                .andExpect(jsonPath("$.errors[0].message").value("닉네임을 입력해 주세요."));
    }

    @Test
    void createProfile_missingSex_returns400WithMessage() throws Exception {
        mockMvc.perform(post("/api/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(Map.of("nickname", "TestUser"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors[0].field").value("sex"))
                .andExpect(jsonPath("$.errors[0].message").value("성별을 입력해 주세요."));
    }

    @Test
    void createProfile_invalidSex_returns400WithMessage() throws Exception {
        mockMvc.perform(post("/api/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(Map.of("nickname", "TestUser", "sex", "X"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors[0].field").value("sex"))
                .andExpect(jsonPath("$.errors[0].message").value("성별은 M 또는 F만 입력 가능합니다."));
    }

    @Test
    void setInstructor_validProfileId_returns200WithId() throws Exception {
        when(setInstructorUseCase.setInstructor(any()))
                .thenReturn(SetInstructorAppResponse.builder().id("Ab2Cd3Ef").build());

        mockMvc.perform(patch("/api/profile/Ab2Cd3Ef/instructor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("Ab2Cd3Ef"));
    }

    @Test
    void setInstructor_nonExistentProfile_returns404() throws Exception {
        when(setInstructorUseCase.setInstructor(any()))
                .thenThrow(new ProfileNotFoundException("NOTEXIST"));

        mockMvc.perform(patch("/api/profile/NOTEXIST/instructor"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.errors[0].field").value("profileId"));
    }

    @Test
    void setInstructor_alreadyInstructor_returns200Idempotently() throws Exception {
        when(setInstructorUseCase.setInstructor(any()))
                .thenReturn(SetInstructorAppResponse.builder().id("Ab2Cd3Ef").build());

        mockMvc.perform(patch("/api/profile/Ab2Cd3Ef/instructor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("Ab2Cd3Ef"));
    }

    @Test
    void getProfiles_noParam_returns200WithAllProfiles() throws Exception {
        GetProfilesAppResponse r1 = GetProfilesAppResponse.builder()
                .id("Ab2Cd3Ef").nickname("홍길동").sex(Sex.M).isInstructor(true).build();
        GetProfilesAppResponse r2 = GetProfilesAppResponse.builder()
                .id("Zx9Yy8Ww").nickname("김영희").sex(Sex.F).isInstructor(false).build();
        when(getProfilesUseCase.getProfiles(isNull())).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/api/profiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("Ab2Cd3Ef"))
                .andExpect(jsonPath("$[0].sex").value("M"))
                .andExpect(jsonPath("$[0].isInstructor").value(true))
                .andExpect(jsonPath("$[1].id").value("Zx9Yy8Ww"));
    }

    @Test
    void getProfiles_noParam_emptyResult_returns200WithEmptyArray() throws Exception {
        when(getProfilesUseCase.getProfiles(isNull())).thenReturn(List.of());

        mockMvc.perform(get("/api/profiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getProfiles_filterInstructor_returns200WithInstructorsOnly() throws Exception {
        GetProfilesAppResponse instructor = GetProfilesAppResponse.builder()
                .id("Ab2Cd3Ef").nickname("홍길동").sex(Sex.M).isInstructor(true).build();
        when(getProfilesUseCase.getProfiles(true)).thenReturn(List.of(instructor));

        mockMvc.perform(get("/api/profiles").param("isInstructor", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isInstructor").value(true));
    }

    @Test
    void getProfiles_invalidParam_returns400WithMessage() throws Exception {
        mockMvc.perform(get("/api/profiles").param("isInstructor", "yes"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors[0].field").value("isInstructor"));
    }
}
