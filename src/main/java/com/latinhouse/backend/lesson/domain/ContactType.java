package com.latinhouse.backend.lesson.domain;

import java.util.Arrays;

public enum ContactType {
    Phone("P"),
    KakaoTalk("K");

    public String code;

    ContactType(String code) { this.code = code; }

    public static ContactType of(String code) {
        return Arrays.stream(ContactType.values())
                .filter(v -> v.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Genre 코드입니다."));
    }
}