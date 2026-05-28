package com.latinhouse.api.profile.application.port.in;

import com.latinhouse.api.profile.domain.Sex;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateProfileAppRequest {

    private final String nickname;
    private final Sex sex;
}
