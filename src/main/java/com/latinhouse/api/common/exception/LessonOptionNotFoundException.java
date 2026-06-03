package com.latinhouse.api.common.exception;

public class LessonOptionNotFoundException extends RuntimeException {

    public LessonOptionNotFoundException(Long lessonOptionNo) {
        super("LessonOption not found: " + lessonOptionNo);
    }
}
