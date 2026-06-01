package com.latinhouse.api.profile.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProfilesWebResponse {

    private final String id;
    private final String nickname;
    private final String sex;
    @JsonProperty("isInstructor")
    private final boolean isInstructor;
}
