package com.latinhouse.api.lesson.domain;

public enum NoticeType {
    LESSON("L"), TIME("T"), REGION("R"), NORMAL("N"), URGENT("U");

    private final String code;

    NoticeType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static NoticeType fromCode(String code) {
        for (NoticeType t : values()) {
            if (t.code.equals(code)) return t;
        }
        throw new IllegalArgumentException("Unknown NoticeType code: " + code);
    }
}
