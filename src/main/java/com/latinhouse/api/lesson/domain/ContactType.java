package com.latinhouse.api.lesson.domain;

public enum ContactType {
    YOUTUBE("Y"), KAKAOTALK("K"), WEB("W"), INSTAGRAM("I"), LINE("L"), MOBILE("M");

    private final String code;

    ContactType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ContactType fromCode(String code) {
        for (ContactType t : values()) {
            if (t.code.equals(code)) return t;
        }
        throw new IllegalArgumentException("Unknown ContactType code: " + code);
    }
}
