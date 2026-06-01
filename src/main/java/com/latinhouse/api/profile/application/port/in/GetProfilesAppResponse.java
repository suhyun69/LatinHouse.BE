package com.latinhouse.api.profile.application.port.in;

import com.latinhouse.api.profile.domain.Sex;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProfilesAppResponse {

    private final String id;
    private final String nickname;
    private final Sex sex;
    private final boolean isInstructor;
}
