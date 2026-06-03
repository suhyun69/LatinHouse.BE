package com.latinhouse.api.lesson.adapter.in.web;

import com.latinhouse.api.common.config.SecurityConfig;
import com.latinhouse.api.common.exception.GlobalExceptionHandler;
import com.latinhouse.api.common.exception.LessonNotFoundException;
import com.latinhouse.api.lesson.application.port.in.CreateLessonUseCase;
import com.latinhouse.api.lesson.application.port.in.CreateRandomLessonUseCase;
import com.latinhouse.api.lesson.application.port.in.GetLessonUseCase;
import com.latinhouse.api.lesson.application.port.in.GetLessonsUseCase;
import com.latinhouse.api.lesson.application.port.in.UpdateLessonAppResponse;
import com.latinhouse.api.lesson.application.port.in.UpdateLessonUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LessonController.class)
@Import({GlobalExceptionHandler.class, SecurityConfig.class})
class UpdateLessonControllerTest {

    private static final String VALID_OPTION =
            "{\"startDate\":\"2026-07-01\",\"startTime\":\"19:00\",\"endDate\":\"2026-07-01\",\"endTime\":\"21:00\",\"region\":\"GN\"}";

    private static final String VALID_BODY =
            "{\"title\":\"살사 중급반\",\"genre\":\"S\",\"instructorLo\":\"Ab2Cd3Ef\",\"options\":[" + VALID_OPTION + "]}";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateLessonUseCase createLessonUseCase;

    @MockitoBean
    private CreateRandomLessonUseCase createRandomLessonUseCase;

    @MockitoBean
    private GetLessonUseCase getLessonUseCase;

    @MockitoBean
    private GetLessonsUseCase getLessonsUseCase;

    @MockitoBean
    private UpdateLessonUseCase updateLessonUseCase;

    @Test
    void updateLesson_validRequest_returns200WithId() throws Exception {
        when(updateLessonUseCase.updateLesson(any()))
                .thenReturn(UpdateLessonAppResponse.builder().id(1L).build());

        mockMvc.perform(put("/api/lesson/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateLesson_missingTitle_returns400() throws Exception {
        String body = "{\"genre\":\"S\",\"instructorLo\":\"Ab2Cd3Ef\",\"options\":[" + VALID_OPTION + "]}";

        mockMvc.perform(put("/api/lesson/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors[0].field").value("title"))
                .andExpect(jsonPath("$.errors[0].message").value("제목을 입력해 주세요."));
    }

    @Test
    void updateLesson_emptyOptions_returns400() throws Exception {
        String body = "{\"title\":\"살사 중급반\",\"genre\":\"S\",\"instructorLo\":\"Ab2Cd3Ef\",\"options\":[]}";

        mockMvc.perform(put("/api/lesson/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void updateLesson_lessonNotFound_returns404() throws Exception {
        when(updateLessonUseCase.updateLesson(any()))
                .thenThrow(new LessonNotFoundException(9999L));

        mockMvc.perform(put("/api/lesson/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.errors[0].field").value("lessonNo"))
                .andExpect(jsonPath("$.errors[0].message").value("레슨을 찾을 수 없습니다."));
    }
}
