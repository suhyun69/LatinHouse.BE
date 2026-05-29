package com.latinhouse.api.lesson.domain;

public enum Region {
    GANGNAM("GN"), HONGDAE("HD");

    private final String code;

    Region(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Region fromCode(String code) {
        for (Region r : values()) {
            if (r.code.equals(code)) return r;
        }
        throw new IllegalArgumentException("Unknown Region code: " + code);
    }
}
