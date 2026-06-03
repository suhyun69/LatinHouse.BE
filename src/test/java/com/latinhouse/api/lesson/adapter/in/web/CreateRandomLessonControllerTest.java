package com.latinhouse.api.lesson.adapter.in.web;

import com.latinhouse.api.common.config.SecurityConfig;
import com.latinhouse.api.common.exception.GlobalExceptionHandler;
import com.latinhouse.api.lesson.application.port.in.CreateLessonUseCase;
import com.latinhouse.api.lesson.application.port.in.CreateRandomLessonAppResponse;
import com.latinhouse.api.lesson.application.port.in.CreateRandomLessonUseCase;
import com.latinhouse.api.lesson.application.port.in.GetLessonUseCase;
import com.latinhouse.api.lesson.application.port.in.GetLessonsUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LessonController.class)
@Import({GlobalExceptionHandler.class, SecurityConfig.class})
class CreateRandomLessonControllerTest {

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

    @Test
    void createRandomLesson_returns201WithId() throws Exception {
        when(createRandomLessonUseCase.createRandomLesson())
                .thenReturn(CreateRandomLessonAppResponse.builder().id(5L).build());

        mockMvc.perform(post("/api/lesson/random"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    void createRandomLesson_onInternalError_returns500() throws Exception {
        when(createRandomLessonUseCase.createRandomLesson())
                .thenThrow(new RuntimeException("강사 생성 실패"));

        mockMvc.perform(post("/api/lesson/random"))
                .andExpect(status().isInternalServerError());
    }
}
