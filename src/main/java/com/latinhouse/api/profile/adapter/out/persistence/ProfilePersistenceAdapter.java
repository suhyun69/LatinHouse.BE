package com.latinhouse.api.profile.adapter.out.persistence;

import com.latinhouse.api.profile.application.port.out.FindProfilePort;
import com.latinhouse.api.profile.application.port.out.SaveProfilePort;
import com.latinhouse.api.profile.application.port.out.UpdateProfilePort;
import com.latinhouse.api.profile.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class ProfilePersistenceAdapter implements SaveProfilePort, FindProfilePort, UpdateProfilePort {

    private final ProfileJpaRepository profileJpaRepository;

    @Override
    public Profile save(Profile profile) {
        ProfileEntity entity = ProfilePersistenceMapper.toEntity(profile);
        ProfileEntity saved = profileJpaRepository.save(entity);
        return ProfilePersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Profile> findById(String profileId) {
        return profileJpaRepository.findById(profileId)
                .map(ProfilePersistenceMapper::toDomain);
    }

    @Override
    public List<Profile> findAll(Boolean isInstructor) {
        List<ProfileEntity> entities = (isInstructor == null)
                ? profileJpaRepository.findAll()
                : profileJpaRepository.findAllByIsInstructor(isInstructor);
        return entities.stream()
                .map(ProfilePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Profile update(Profile profile) {
        ProfileEntity entity = ProfilePersistenceMapper.toEntity(profile);
        ProfileEntity saved = profileJpaRepository.save(entity);
        return ProfilePersistenceMapper.toDomain(saved);
    }
}
