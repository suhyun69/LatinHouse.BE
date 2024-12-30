package com.latinhouse.backend.profile.port.in.response;

import com.latinhouse.backend.profile.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // test
public class CreateProfileAppResponse {
    private String id;
    private String nickname;
    private String sex;
    private Boolean isInstructor;

    public CreateProfileAppResponse(Profile profile) {
        this.id = profile.getId();
        this.nickname = profile.getNickname();
        this.sex = profile.getSex().name();
        this.isInstructor = profile.getIsInstructor();
    }
}