package com.latinhouse.api.profile.application.port.in;

import com.latinhouse.api.profile.domain.Profile;

public class SetInstructorAppMapper {

    private SetInstructorAppMapper() {}

    public static SetInstructorAppResponse toAppResponse(Profile profile) {
        return SetInstructorAppResponse.builder()
                .id(profile.getId())
                .build();
    }
}
