package com.latinhouse.api.profile.adapter.in.web;

import com.latinhouse.api.profile.application.port.in.SetInstructorAppRequest;
import com.latinhouse.api.profile.application.port.in.SetInstructorAppResponse;

public class SetInstructorWebMapper {

    private SetInstructorWebMapper() {}

    public static SetInstructorAppRequest toAppRequest(String profileId) {
        return SetInstructorAppRequest.builder()
                .profileId(profileId)
                .build();
    }

    public static SetInstructorWebResponse toWebResponse(SetInstructorAppResponse appResponse) {
        return SetInstructorWebResponse.builder()
                .id(appResponse.getId())
                .build();
    }
}
