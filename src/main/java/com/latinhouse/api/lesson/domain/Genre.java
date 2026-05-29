package com.latinhouse.api.lesson.domain;

public enum Genre {
    SALSA("S"), BACHATA("B");

    private final String code;

    Genre(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Genre fromCode(String code) {
        for (Genre g : values()) {
            if (g.code.equals(code)) return g;
        }
        throw new IllegalArgumentException("Unknown Genre code: " + code);
    }
}
