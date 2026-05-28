package com.latinhouse.api.profile.application.port.in;

import com.latinhouse.api.profile.domain.Profile;

public class CreateProfileAppMapper {

    private CreateProfileAppMapper() {}

    public static Profile toDomain(CreateProfileAppRequest request, String id) {
        return Profile.builder()
                .id(id)
                .nickname(request.getNickname())
                .sex(request.getSex())
                .isInstructor(false)
                .build();
    }

    public static CreateProfileAppResponse toAppResponse(Profile profile) {
        return CreateProfileAppResponse.builder()
                .id(profile.getId())
                .build();
    }
}
