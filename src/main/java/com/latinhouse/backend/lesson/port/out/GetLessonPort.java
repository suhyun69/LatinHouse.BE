package com.latinhouse.backend.lesson.port.out;

import com.latinhouse.backend.lesson.domain.Lesson;
import com.latinhouse.backend.profile.domain.Profile;

import java.util.List;

public interface GetLessonPort {
    Lesson getLessonById(Long id);
    List<Lesson> getAllLessons();
}
