package com.latinhouse.backend.profile.application;

import com.latinhouse.backend.profile.domain.Profile;
import com.latinhouse.backend.profile.port.in.CreateProfileUseCase;
import com.latinhouse.backend.profile.port.out.SaveProfilePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProfileService implements CreateProfileUseCase {

    private final SaveProfilePort saveProfilePort;

    @Override
    public Profile createProfile(Profile profile) {
        // 비즈니스 로직 구현 예:
        if (profile.getName() == null || profile.getEmail() == null) {
            throw new IllegalArgumentException("Name and Email are required!");
        }
        return saveProfilePort.saveProfile(profile);
    }
}