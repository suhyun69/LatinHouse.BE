package com.latinhouse.backend.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;

@Slf4j
// @RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    private final HttpStatus HTTP_BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private final HttpStatus HTTP_INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    /**
     * @Valid Annotation에서 발생한 에러
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        String bodyOfResponse = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        final ErrorResponse errorResponse = new ErrorResponse(bodyOfResponse);
        return new ResponseEntity<>(errorResponse, HTTP_BAD_REQUEST);
    }

    /**
     * BadRequestException에서 발생한 에러
     * appRequest에서 호출하는 selfValildating에서 발생한 에러
     *
     * @param ex BadRequestException
     * @return ApiResponse
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HTTP_BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> handleDateTimeParseException(DateTimeParseException ex) {
        ErrorResponse errorResponse = new ErrorResponse(String.format("'%s'을 시간으로 변환할 수 없습니다.", ex.getParsedString()));
        return new ResponseEntity<>(errorResponse, HTTP_BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleCustomException(BusinessException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getErrorCode().getStatus()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    protected final ResponseEntity<?> handleAllExceptions(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HTTP_INTERNAL_SERVER_ERROR);
    }
}