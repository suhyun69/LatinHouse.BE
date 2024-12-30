package com.latinhouse.backend.profile.application;

import com.latinhouse.backend.profile.domain.Profile;
import com.latinhouse.backend.profile.port.in.CreateProfileUseCase;
import com.latinhouse.backend.profile.port.in.GetProfileUseCase;
import com.latinhouse.backend.profile.port.in.request.CreateProfileAppRequest;
import com.latinhouse.backend.profile.port.in.response.CreateProfileAppResponse;
import com.latinhouse.backend.profile.port.in.response.GetProfileAppResponse;
import com.latinhouse.backend.profile.port.out.GetProfilePort;
import com.latinhouse.backend.profile.port.out.SaveProfilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService implements
        CreateProfileUseCase,
        GetProfileUseCase
{

    private final SaveProfilePort saveProfilePort;
    private final GetProfilePort getProfilePort;

    @Override
    public CreateProfileAppResponse createProfile(CreateProfileAppRequest appReq) {
        Profile profile = saveProfilePort.saveProfile(appReq);
        return new CreateProfileAppResponse(profile);
    }

    @Override
    public GetProfileAppResponse getProfileById(String id) {
        Profile profile = getProfilePort.getProfileById(id);
        return new GetProfileAppResponse(profile);
    }

    @Override
    public List<GetProfileAppResponse> getAllProfiles() {
        List<Profile> profileList = getProfilePort.getAllProfiles();
        return profileList.stream().map(GetProfileAppResponse::new).toList();
    }
}