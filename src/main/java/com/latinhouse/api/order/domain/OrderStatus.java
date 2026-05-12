package com.latinhouse.api.order.domain;

import com.latinhouse.api.global.exception.CustomException;
import com.latinhouse.api.global.exception.ErrorCode;

public enum OrderStatus {

    APPLIED,    // 지원완료
    PAYMENTED,  // 결제완료
    APPROVED,   // 승인완료
    CANCELED;   // 취소

    public static OrderStatus of(String value) {
        try {
            return OrderStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
    }
}
