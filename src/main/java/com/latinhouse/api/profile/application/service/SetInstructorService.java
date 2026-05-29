package com.latinhouse.api.profile.application.service;

import com.latinhouse.api.common.exception.ProfileNotFoundException;
import com.latinhouse.api.profile.application.port.in.SetInstructorAppMapper;
import com.latinhouse.api.profile.application.port.in.SetInstructorAppRequest;
import com.latinhouse.api.profile.application.port.in.SetInstructorAppResponse;
import com.latinhouse.api.profile.application.port.in.SetInstructorUseCase;
import com.latinhouse.api.profile.application.port.out.FindProfilePort;
import com.latinhouse.api.profile.application.port.out.UpdateProfilePort;
import com.latinhouse.api.profile.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SetInstructorService implements SetInstructorUseCase {

    private final FindProfilePort findProfilePort;
    private final UpdateProfilePort updateProfilePort;

    @Override
    @Transactional
    public SetInstructorAppResponse setInstructor(SetInstructorAppRequest request) {
        Profile profile = findProfilePort.findById(request.getProfileId())
                .orElseThrow(() -> new ProfileNotFoundException(request.getProfileId()));
        Profile updated = updateProfilePort.update(profile.asInstructor());
        return SetInstructorAppMapper.toAppResponse(updated);
    }
}
