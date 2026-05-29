package com.latinhouse.api.profile.application.port.in;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SetInstructorAppRequest {

    private final String profileId;
}
