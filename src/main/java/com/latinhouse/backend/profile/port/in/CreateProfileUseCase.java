package com.latinhouse.backend.profile.port.in;

import com.latinhouse.backend.profile.port.in.request.CreateProfileAppRequest;
import com.latinhouse.backend.profile.port.in.response.CreateProfileAppResponse;

public interface CreateProfileUseCase {
    CreateProfileAppResponse createProfile(CreateProfileAppRequest appReq);
}