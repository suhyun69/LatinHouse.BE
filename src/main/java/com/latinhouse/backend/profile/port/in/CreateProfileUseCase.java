package com.latinhouse.backend.profile.port.in;

import com.latinhouse.backend.profile.domain.Profile;

public interface CreateProfileUseCase {
    Profile createProfile(Profile profile);
}