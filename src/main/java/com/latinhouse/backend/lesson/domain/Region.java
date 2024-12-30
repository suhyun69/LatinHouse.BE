package com.latinhouse.backend.lesson.domain;

import java.util.Arrays;

public enum Region {
    홍대("HD"),
    강남("GN");

    public String code;

    Region(String code) { this.code = code; }

    public static Region of(String code) {
        return Arrays.stream(Region.values())
                .filter(v -> v.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Region 코드입니다."));
    }
}