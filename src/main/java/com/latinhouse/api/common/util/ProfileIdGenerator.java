package com.latinhouse.api.common.util;

import java.security.SecureRandom;

public class ProfileIdGenerator {

    // Excludes visually ambiguous characters: i I l 0 1 o O
    private static final String CHARSET =
            "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789";
    private static final int LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    private ProfileIdGenerator() {}

    public static String generate() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARSET.charAt(RANDOM.nextInt(CHARSET.length())));
        }
        return sb.toString();
    }
}
