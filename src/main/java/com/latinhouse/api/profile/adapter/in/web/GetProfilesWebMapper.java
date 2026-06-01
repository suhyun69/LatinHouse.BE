package com.latinhouse.api.profile.adapter.in.web;

import com.latinhouse.api.profile.application.port.in.GetProfilesAppResponse;

public class GetProfilesWebMapper {

    private GetProfilesWebMapper() {}

    public static GetProfilesWebResponse toWebResponse(GetProfilesAppResponse appResponse) {
        return GetProfilesWebResponse.builder()
                .id(appResponse.getId())
                .nickname(appResponse.getNickname())
                .sex(appResponse.getSex().name())
                .isInstructor(appResponse.isInstructor())
                .build();
    }
}
