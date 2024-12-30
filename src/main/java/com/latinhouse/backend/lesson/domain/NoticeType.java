package com.latinhouse.backend.lesson.domain;

import java.util.Arrays;

public enum NoticeType {

    Price("P"),
    Date("D"),
    Normal("N");

    public String code;

    NoticeType(String code) { this.code = code; }

    public static NoticeType of(String code) {
        return Arrays.stream(NoticeType.values())
                .filter(v -> v.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 NoticeType 코드입니다."));
    }
}