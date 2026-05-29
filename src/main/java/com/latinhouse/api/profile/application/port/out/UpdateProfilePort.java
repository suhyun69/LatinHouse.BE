package com.latinhouse.api.profile.application.port.out;

import com.latinhouse.api.profile.domain.Profile;

public interface UpdateProfilePort {

    Profile update(Profile profile);
}
