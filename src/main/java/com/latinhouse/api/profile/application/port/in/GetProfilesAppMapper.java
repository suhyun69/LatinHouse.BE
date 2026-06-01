package com.latinhouse.api.profile.application.port.in;

import com.latinhouse.api.profile.domain.Profile;

public class GetProfilesAppMapper {

    private GetProfilesAppMapper() {}

    public static GetProfilesAppResponse toAppResponse(Profile profile) {
        return GetProfilesAppResponse.builder()
                .id(profile.getId())
                .nickname(profile.getNickname())
                .sex(profile.getSex())
                .isInstructor(profile.isInstructor())
                .build();
    }
}
