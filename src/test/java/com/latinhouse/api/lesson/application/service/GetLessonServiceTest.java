package com.latinhouse.api.lesson.application.service;

import com.latinhouse.api.common.exception.LessonNotFoundException;
import com.latinhouse.api.lesson.application.port.in.GetLessonAppResponse;
import com.latinhouse.api.lesson.application.port.out.LoadLessonPort;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.domain.LessonOption;
import com.latinhouse.api.lesson.domain.Region;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetLessonServiceTest {

    @Mock
    private LoadLessonPort loadLessonPort;

    @InjectMocks
    private GetLessonService getLessonService;

    private Lesson sampleLesson() {
        return Lesson.builder()
                .id(1L)
                .title("살사 초급반")
                .genre(Genre.SALSA)
                .instructorLo("Ab2Cd3Ef")
                .instructorLa(null)
                .options(List.of(LessonOption.builder()
                        .id(1L)
                        .startDateTime(LocalDateTime.of(2026, 6, 1, 10, 0))
                        .endDateTime(LocalDateTime.of(2026, 6, 1, 12, 0))
                        .region(Region.GANGNAM)
                        .build()))
                .discounts(List.of())
                .account(null)
                .contacts(List.of())
                .isActive(true)
                .notices(List.of())
                .build();
    }

    @Test
    void getLesson_found_returnsAppResponse() {
        when(loadLessonPort.loadLesson(1L)).thenReturn(sampleLesson());

        GetLessonAppResponse response = getLessonService.getLesson(1L);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("살사 초급반");
        assertThat(response.getGenre()).isEqualTo(Genre.SALSA);
        assertThat(response.getInstructorLo()).isEqualTo("Ab2Cd3Ef");
        assertThat(response.getInstructorLa()).isNull();
        assertThat(response.getOptions()).hasSize(1);
        assertThat(response.getDiscounts()).isEmpty();
        assertThat(response.getAccount()).isNull();
        assertThat(response.getContacts()).isEmpty();
        assertThat(response.getNotices()).isEmpty();
    }

    @Test
    void getLesson_notFound_throwsLessonNotFoundException() {
        when(loadLessonPort.loadLesson(9999L)).thenThrow(new LessonNotFoundException(9999L));

        assertThatThrownBy(() -> getLessonService.getLesson(9999L))
                .isInstanceOf(LessonNotFoundException.class);
    }
}
