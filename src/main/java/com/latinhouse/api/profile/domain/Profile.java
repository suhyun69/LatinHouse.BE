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
}
