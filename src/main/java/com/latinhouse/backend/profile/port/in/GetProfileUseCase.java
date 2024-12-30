package com.latinhouse.backend.profile.port.in;

import com.latinhouse.backend.profile.port.in.response.GetProfileAppResponse;

import java.util.List;

public interface GetProfileUseCase {
    GetProfileAppResponse getProfileById(String id);
    List<GetProfileAppResponse> getAllProfiles();
}
