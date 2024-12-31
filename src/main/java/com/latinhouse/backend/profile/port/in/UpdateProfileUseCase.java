package com.latinhouse.backend.profile.port.in;

import com.latinhouse.backend.profile.port.in.response.GetProfileAppResponse;

public interface UpdateProfileUseCase {
    GetProfileAppResponse updateIsInstructor(String id);
}
