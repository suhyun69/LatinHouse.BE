package com.latinhouse.api.profile.adapter.out.persistence;

import com.latinhouse.api.profile.application.port.out.SaveProfilePort;
import com.latinhouse.api.profile.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ProfilePersistenceAdapter implements SaveProfilePort {

    private final ProfileJpaRepository profileJpaRepository;

    @Override
    public Profile save(Profile profile) {
        ProfileEntity entity = ProfilePersistenceMapper.toEntity(profile);
        ProfileEntity saved = profileJpaRepository.save(entity);
        return ProfilePersistenceMapper.toDomain(saved);
    }
}
