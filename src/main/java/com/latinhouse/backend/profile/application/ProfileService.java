package com.latinhouse.backend.profile.application;

import com.latinhouse.backend.profile.domain.Profile;
import com.latinhouse.backend.profile.port.in.CreateProfileUseCase;
import com.latinhouse.backend.profile.port.in.request.CreateProfileAppRequest;
import com.latinhouse.backend.profile.port.in.response.CreateProfileAppResponse;
import com.latinhouse.backend.profile.port.out.SaveProfilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService implements CreateProfileUseCase {

    private final SaveProfilePort saveProfilePort;

    @Override
    public CreateProfileAppResponse createProfile(CreateProfileAppRequest appReq) {
        Profile profile = saveProfilePort.saveProfile(appReq);
        return new CreateProfileAppResponse(profile);
    }
}