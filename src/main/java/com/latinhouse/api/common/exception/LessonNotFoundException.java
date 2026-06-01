package com.latinhouse.api.common.exception;

public class LessonNotFoundException extends RuntimeException {

    public LessonNotFoundException(Long lessonNo) {
        super("Lesson not found: " + lessonNo);
    }
}
