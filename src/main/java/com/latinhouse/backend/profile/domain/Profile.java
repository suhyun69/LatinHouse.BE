package com.latinhouse.backend.profile.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Data
@Builder
@NoArgsConstructor
// @AllArgsConstructor
public class Profile {

    // 닉네임 검증 정규식
    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{3,50}$");

    // 비밀번호 검증 정규식: 숫자, 대문자, 소문자, 특수문자 포함
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    private String id;
    private String nickname;
    private String password;
    private Sex sex;
    private Boolean isInstructor;

    public Profile(String id, String nickname, String password, Sex sex, Boolean isInstructor) {
        validateNickname(nickname);
        // validatePassword(password);
        validateSex(sex);
        validateInstructor(isInstructor);

        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.sex = sex;
        this.isInstructor = isInstructor;
    }

    private void validateNickname(String nickname) {
        if (nickname == null) {
            throw new IllegalArgumentException(
                    "nickname cannot be null"
            );
        }
    }

    /*
    private void validatePassword(String password) {
        if (password == null || !PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters long and include a number, an uppercase letter, a lowercase letter, and a special character."
            );
        }
    }
    */

    private void validateSex(Sex sex) {
        if (sex != Sex.Male && sex != Sex.Female) {
            throw new IllegalArgumentException("sex must be either 'Male' or 'Female'.");
        }
    }

    private void validateInstructor(Boolean isInstructor) {
        if (isInstructor == null) {
            throw new IllegalArgumentException("isInstructor status cannot be null.");
        }
    }
}