package com.latinhouse.api.lesson.adapter.in.web;

import com.latinhouse.api.common.config.SecurityConfig;
import com.latinhouse.api.common.exception.GlobalExceptionHandler;
import com.latinhouse.api.common.exception.LessonNotFoundException;
import com.latinhouse.api.lesson.application.port.in.CreateLessonAppResponse;
import com.latinhouse.api.lesson.application.port.in.CreateLessonUseCase;
import com.latinhouse.api.lesson.application.port.in.GetLessonAppResponse;
import com.latinhouse.api.lesson.application.port.in.GetLessonUseCase;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.Region;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @MockitoBean
    private GetLessonUseCase getLessonUseCase;

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

    @Test
    void getLesson_existingId_returns200WithFullBody() throws Exception {
        GetLessonAppResponse appResponse = GetLessonAppResponse.builder()
                .id(1L)
                .title("살사 초급반")
                .genre(Genre.SALSA)
                .instructorLo("Ab2Cd3Ef")
                .instructorLa(null)
                .options(List.of(GetLessonAppResponse.OptionResponse.builder()
                        .id(1L)
                        .startDateTime(LocalDateTime.of(2026, 6, 1, 10, 0))
                        .endDateTime(LocalDateTime.of(2026, 6, 1, 12, 0))
                        .region(Region.GANGNAM)
                        .place("강남 스튜디오")
                        .placeUrl(null)
                        .build()))
                .discounts(List.of())
                .account(null)
                .contacts(List.of())
                .isActive(true)
                .notices(List.of())
                .build();

        when(getLessonUseCase.getLesson(1L)).thenReturn(appResponse);

        mockMvc.perform(get("/api/lessons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("살사 초급반"))
                .andExpect(jsonPath("$.genre").value("S"))
                .andExpect(jsonPath("$.instructorLo").value("Ab2Cd3Ef"))
                .andExpect(jsonPath("$.instructorLa").isEmpty())
                .andExpect(jsonPath("$.options[0].id").value(1))
                .andExpect(jsonPath("$.options[0].startDate").value("2026-06-01"))
                .andExpect(jsonPath("$.options[0].startTime").value("10:00"))
                .andExpect(jsonPath("$.options[0].endDate").value("2026-06-01"))
                .andExpect(jsonPath("$.options[0].endTime").value("12:00"))
                .andExpect(jsonPath("$.options[0].region").value("GN"))
                .andExpect(jsonPath("$.discounts").isEmpty())
                .andExpect(jsonPath("$.account").doesNotExist())
                .andExpect(jsonPath("$.contacts").isEmpty())
                .andExpect(jsonPath("$.isActive").value(true))
                .andExpect(jsonPath("$.notices").isEmpty());
    }

    @Test
    void getLesson_notExistingId_returns404() throws Exception {
        when(getLessonUseCase.getLesson(9999L)).thenThrow(new LessonNotFoundException(9999L));

        mockMvc.perform(get("/api/lessons/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.errors[0].field").value("lessonNo"))
                .andExpect(jsonPath("$.errors[0].message").value("레슨을 찾을 수 없습니다."));
    }
}
