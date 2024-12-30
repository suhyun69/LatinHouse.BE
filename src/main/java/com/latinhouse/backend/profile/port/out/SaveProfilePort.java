package com.latinhouse.backend.profile.port.out;

import com.latinhouse.backend.profile.domain.Profile;
import com.latinhouse.backend.profile.port.in.request.CreateProfileAppRequest;

public interface SaveProfilePort {
    Profile saveProfile(CreateProfileAppRequest profile);
}