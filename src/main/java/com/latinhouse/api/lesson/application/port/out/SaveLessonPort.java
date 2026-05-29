package com.latinhouse.api.lesson.application.port.out;

import com.latinhouse.api.lesson.domain.Lesson;

public interface SaveLessonPort {
    Lesson save(Lesson lesson);
}
