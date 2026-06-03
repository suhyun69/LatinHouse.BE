package com.latinhouse.api.lesson.application.port.out;

import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.domain.Region;

import java.util.List;

public interface LoadLessonsPort {
    List<Lesson> loadLessons(Region region, String instructor, Genre genre);
}
