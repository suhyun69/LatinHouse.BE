package com.latinhouse.api.common.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ErrorResponse {

    private final int status;
    private final List<FieldError> errors;

    @Getter
    @Builder
    public static class FieldError {
        private final String field;
        private final String message;
    }
}
