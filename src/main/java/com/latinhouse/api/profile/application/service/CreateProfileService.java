package com.latinhouse.api.profile.application.service;

import com.latinhouse.api.common.util.ProfileIdGenerator;
import com.latinhouse.api.profile.application.port.in.CreateProfileAppMapper;
import com.latinhouse.api.profile.application.port.in.CreateProfileAppRequest;
import com.latinhouse.api.profile.application.port.in.CreateProfileAppResponse;
import com.latinhouse.api.profile.application.port.in.CreateProfileUseCase;
import com.latinhouse.api.profile.application.port.out.SaveProfilePort;
import com.latinhouse.api.profile.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProfileService implements CreateProfileUseCase {

    private final SaveProfilePort saveProfilePort;

    @Override
    public CreateProfileAppResponse createProfile(CreateProfileAppRequest request) {
        Profile profile = CreateProfileAppMapper.toDomain(request, ProfileIdGenerator.generate());
        Profile saved = saveProfilePort.save(profile);
        return CreateProfileAppMapper.toAppResponse(saved);
    }
}
