package com.latinhouse.api.common.exception;

import java.util.List;

public class LessonValidationException extends RuntimeException {

    private final List<ErrorResponse.FieldError> errors;

    public LessonValidationException(List<ErrorResponse.FieldError> errors) {
        super("Lesson validation failed");
        this.errors = errors;
    }

    public List<ErrorResponse.FieldError> getErrors() {
        return errors;
    }
}
