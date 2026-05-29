package com.latinhouse.api.profile.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Profile {

    private final String id;
    private final String nickname;
    private final Sex sex;
    private final boolean isInstructor;

    public Profile asInstructor() {
        return Profile.builder()
                .id(this.id)
                .nickname(this.nickname)
                .sex(this.sex)
                .isInstructor(true)
                .build();
    }
}
