package com.latinhouse.api.lesson.application.port.out;

import com.latinhouse.api.profile.domain.Profile;

import java.util.Optional;

public interface LoadInstructorPort {
    Optional<Profile> findById(String profileId);
}
