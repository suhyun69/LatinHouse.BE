package com.latinhouse.api.lesson.adapter.in.web;

import com.latinhouse.api.common.config.SecurityConfig;
import com.latinhouse.api.common.exception.GlobalExceptionHandler;
import com.latinhouse.api.lesson.application.port.in.CreateLessonAppResponse;
import com.latinhouse.api.lesson.application.port.in.CreateLessonUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LessonController.class)
@Import({GlobalExceptionHandler.class, SecurityConfig.class})
class LessonControllerTest {

    private static final String VALID_OPTION =
            "{\"startDate\":\"2026-06-01\",\"startTime\":\"10:00\",\"endDate\":\"2026-06-01\",\"endTime\":\"12:00\",\"region\":\"GN\"}";

    private static final String VALID_BODY =
            "{\"title\":\"테스트 레슨\",\"genre\":\"S\",\"instructorLo\":\"inst1\",\"options\":[" + VALID_OPTION + "]}";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateLessonUseCase createLessonUseCase;

    @Test
    void createLesson_validRequest_returns201WithId() throws Exception {
        when(createLessonUseCase.createLesson(any()))
                .thenReturn(CreateLessonAppResponse.builder().id(1L).build());

        mockMvc.perform(post("/api/lesson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void createLesson_missingTitle_returns400() throws Exception {
        String body = "{\"genre\":\"S\",\"instructorLo\":\"inst1\",\"options\":[" + VALID_OPTION + "]}";

        mockMvc.perform(post("/api/lesson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors[0].field").value("title"))
                .andExpect(jsonPath("$.errors[0].message").value("제목을 입력해 주세요."));
    }

    @Test
    void createLesson_invalidGenre_returns400() throws Exception {
        String body = "{\"title\":\"테스트 레슨\",\"genre\":\"X\",\"instructorLo\":\"inst1\",\"options\":[" + VALID_OPTION + "]}";

        mockMvc.perform(post("/api/lesson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void createLesson_emptyOptions_returns400() throws Exception {
        String body = "{\"title\":\"테스트 레슨\",\"genre\":\"S\",\"instructorLo\":\"inst1\",\"options\":[]}";

        mockMvc.perform(post("/api/lesson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void createLesson_invalidStartDateFormat_returns400() throws Exception {
        String badOption =
                "{\"startDate\":\"20260601\",\"startTime\":\"10:00\",\"endDate\":\"2026-06-01\",\"endTime\":\"12:00\",\"region\":\"GN\"}";
        String body = "{\"title\":\"테스트 레슨\",\"genre\":\"S\",\"instructorLo\":\"inst1\",\"options\":[" + badOption + "]}";

        mockMvc.perform(post("/api/lesson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void createLesson_invalidRegion_returns400() throws Exception {
        String badOption =
                "{\"startDate\":\"2026-06-01\",\"startTime\":\"10:00\",\"endDate\":\"2026-06-01\",\"endTime\":\"12:00\",\"region\":\"SE\"}";
        String body = "{\"title\":\"테스트 레슨\",\"genre\":\"S\",\"instructorLo\":\"inst1\",\"options\":[" + badOption + "]}";

        mockMvc.perform(post("/api/lesson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }
}
