package com.latinhouse.backend.profile.adapter.out.persistence;

import com.latinhouse.backend.profile.adapter.out.persistence.entity.ProfileJpaEntity;
import com.latinhouse.backend.profile.adapter.out.persistence.mapper.ProfileJpaMapper;
import com.latinhouse.backend.profile.adapter.out.persistence.repository.ProfileJpaRepository;
import com.latinhouse.backend.profile.domain.Profile;
import com.latinhouse.backend.profile.port.in.request.CreateProfileAppRequest;
import com.latinhouse.backend.profile.port.out.GetProfilePort;
import com.latinhouse.backend.profile.port.out.SaveProfilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfilePersistenceAdapter implements
        SaveProfilePort,
        GetProfilePort
{

    private final ProfileJpaMapper profileJpaMapper;
    private final ProfileJpaRepository profileJpaRepository;

    @Override
    public Profile saveProfile(CreateProfileAppRequest req) {
        ProfileJpaEntity profileT = profileJpaRepository.save(profileJpaMapper.mapToJpaEntity(req));
        return profileJpaMapper.mapToDomainEntity(profileT);
    }

    @Override
    public Profile getProfileById(String id) {
        ProfileJpaEntity profileT = profileJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profile ID " + id + " not found"));
        return profileJpaMapper.mapToDomainEntity(profileT);
    }

    @Override
    public List<Profile> getAllProfiles() {
        List<ProfileJpaEntity> profileTList = profileJpaRepository.findAll();
        return profileTList.stream().map(profileJpaMapper::mapToDomainEntity).toList();
    }
}