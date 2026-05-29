package com.latinhouse.api.profile.application.port.out;

import com.latinhouse.api.profile.domain.Profile;

import java.util.Optional;

public interface FindProfilePort {

    Optional<Profile> findById(String profileId);
}
