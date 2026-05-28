package com.latinhouse.api.profile.application.port.out;

import com.latinhouse.api.profile.domain.Profile;

public interface SaveProfilePort {

    Profile save(Profile profile);
}
