package com.latinhouse.api.profile.adapter.in.web;

import com.latinhouse.api.profile.application.port.in.CreateProfileAppRequest;
import com.latinhouse.api.profile.application.port.in.CreateProfileAppResponse;
import com.latinhouse.api.profile.domain.Sex;

public class CreateProfileWebMapper {

    private CreateProfileWebMapper() {}

    public static CreateProfileAppRequest toAppRequest(CreateProfileWebRequest webRequest) {
        return CreateProfileAppRequest.builder()
                .nickname(webRequest.getNickname())
                .sex(Sex.valueOf(webRequest.getSex()))
                .build();
    }

    public static CreateProfileWebResponse toWebResponse(CreateProfileAppResponse appResponse) {
        return CreateProfileWebResponse.builder()
                .id(appResponse.getId())
                .build();
    }
}
