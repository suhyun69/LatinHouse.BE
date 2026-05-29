package com.latinhouse.api.lesson.domain;

public enum DiscountType {
    EARLYBIRD("E"), SEX("S");

    private final String code;

    DiscountType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static DiscountType fromCode(String code) {
        for (DiscountType t : values()) {
            if (t.code.equals(code)) return t;
        }
        throw new IllegalArgumentException("Unknown DiscountType code: " + code);
    }
}
