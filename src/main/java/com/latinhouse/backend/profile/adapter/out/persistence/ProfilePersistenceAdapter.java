package com.latinhouse.backend.profile.adapter.out.persistence;

import com.latinhouse.backend.profile.adapter.out.persistence.entity.ProfileJpaEntity;
import com.latinhouse.backend.profile.adapter.out.persistence.entity.ProfileJpaMapper;
import com.latinhouse.backend.profile.adapter.out.persistence.repository.ProfileJpaRepository;
import com.latinhouse.backend.profile.domain.Profile;
import com.latinhouse.backend.profile.port.in.request.CreateProfileAppRequest;
import com.latinhouse.backend.profile.port.out.SaveProfilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfilePersistenceAdapter implements SaveProfilePort {

    private final ProfileJpaMapper profileJpaMapper;
    private final ProfileJpaRepository profileJpaRepository;

    @Override
    public Profile saveProfile(CreateProfileAppRequest req) {
        ProfileJpaEntity profileT = profileJpaMapper.mapToJpaEntity(req);
        return profileJpaMapper.mapToDomainEntity(profileJpaRepository.save(profileT));
    }
}