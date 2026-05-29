package com.latinhouse.api.lesson.adapter.out.persistence;

import com.latinhouse.api.lesson.application.port.out.LoadInstructorPort;
import com.latinhouse.api.profile.adapter.out.persistence.ProfileJpaRepository;
import com.latinhouse.api.profile.adapter.out.persistence.ProfilePersistenceMapper;
import com.latinhouse.api.profile.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class InstructorPersistenceAdapter implements LoadInstructorPort {

    private final ProfileJpaRepository profileJpaRepository;

    @Override
    public Optional<Profile> findById(String profileId) {
        return profileJpaRepository.findById(profileId)
                .map(ProfilePersistenceMapper::toDomain);
    }
}
