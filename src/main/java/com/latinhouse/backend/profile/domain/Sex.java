package com.latinhouse.backend.profile.domain;

import java.util.Arrays;

public enum Sex {
    Male("M"),
    Female("F");

    private final String code;

    Sex(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Sex of(String code) {
        return Arrays.stream(Sex.values())
                .filter(v -> v.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 성별 코드입니다."));
    }
}