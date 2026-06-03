package com.latinhouse.api.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        List<ErrorResponse.FieldError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> ErrorResponse.FieldError.builder()
                        .field(fe.getField())
                        .message(fe.getDefaultMessage())
                        .build())
                .toList();

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errors(errors)
                .build();
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleProfileNotFoundException(ProfileNotFoundException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .errors(List.of(ErrorResponse.FieldError.builder()
                        .field("profileId")
                        .message("프로필을 찾을 수 없습니다.")
                        .build()))
                .build();
    }

    @ExceptionHandler(LessonValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleLessonValidationException(LessonValidationException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errors(ex.getErrors())
                .build();
    }

    @ExceptionHandler(InvalidParamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidParamException(InvalidParamException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errors(List.of(ErrorResponse.FieldError.builder()
                        .field(ex.getField())
                        .message(ex.getMessage())
                        .build()))
                .build();
    }

    @ExceptionHandler(LessonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleLessonNotFoundException(LessonNotFoundException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .errors(List.of(ErrorResponse.FieldError.builder()
                        .field("lessonNo")
                        .message("레슨을 찾을 수 없습니다.")
                        .build()))
                .build();
    }

    @ExceptionHandler(LessonOptionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleLessonOptionNotFoundException(LessonOptionNotFoundException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .errors(List.of(ErrorResponse.FieldError.builder()
                        .field("lessonOptionNo")
                        .message("레슨 옵션을 찾을 수 없습니다.")
                        .build()))
                .build();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errors(List.of(ErrorResponse.FieldError.builder()
                        .field(null)
                        .message("서버 내부 오류가 발생했습니다.")
                        .build()))
                .build();
    }
}
