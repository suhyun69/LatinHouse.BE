package com.latinhouse.backend.profile.adapter.in.web.response;

import com.latinhouse.backend.profile.port.in.response.GetProfileAppResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetProfileWebResponse {
    private String id;
    private String nickname;
    private String sex;
    private Boolean isInstructor;

    public GetProfileWebResponse(GetProfileAppResponse appRes) {
        this.id = appRes.getId();
        this.nickname = appRes.getNickname();
        this.sex = appRes.getSex();
        this.isInstructor = appRes.getIsInstructor();
    }
}
