package com.latinhouse.backend.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    PROFILE_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    INSTRUCTOR_NOT_FOUND(404, "강사를 찾을 수 없습니다."),
    LESSON_NOT_FOUND(404, "수업를 찾을 수 없습니다.")
    ; // End

    private final int status;
    private final String message;

    // 생성자 구성
    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
}
