package com.latinhouse.backend.lesson.domain;

import java.util.Arrays;

public enum DiscountType {
    EarlyBird("E");

    public String code;

    DiscountType(String code) { this.code = code; }

    public static DiscountType of(String code) {
        return Arrays.stream(DiscountType.values())
                .filter(v -> v.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 DiscountType 코드입니다."));
    }
}
