package com.latinhouse.api.common.exception;

import lombok.Getter;

@Getter
public class InvalidParamException extends RuntimeException {

    private final String field;

    public InvalidParamException(String field, String message) {
        super(message);
        this.field = field;
    }
}
