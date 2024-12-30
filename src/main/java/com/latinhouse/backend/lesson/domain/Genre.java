package com.latinhouse.backend.lesson.domain;

import java.util.Arrays;

public enum Genre {
    Salsa("S"),
    Bachata("B");

    public String code;

    Genre(String code) { this.code = code; }

    public static Genre of(String code) {
        return Arrays.stream(Genre.values())
                .filter(v -> v.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Genre 코드입니다."));
    }
}