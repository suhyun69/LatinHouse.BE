package com.latinhouse.api.profile.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.latinhouse.api.common.config.SecurityConfig;
import com.latinhouse.api.common.exception.GlobalExceptionHandler;
import com.latinhouse.api.profile.application.port.in.CreateProfileAppResponse;
import com.latinhouse.api.profile.application.port.in.CreateProfileUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
}
