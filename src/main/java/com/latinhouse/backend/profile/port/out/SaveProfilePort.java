package com.latinhouse.backend.profile.port.out;

import com.latinhouse.backend.profile.domain.Profile;

public interface SaveProfilePort {
    Profile saveProfile(Profile profile);
}