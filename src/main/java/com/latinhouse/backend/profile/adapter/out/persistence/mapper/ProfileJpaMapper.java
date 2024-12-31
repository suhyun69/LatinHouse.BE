package com.latinhouse.backend.profile.adapter.out.persistence.mapper;

import com.latinhouse.backend.profile.adapter.out.persistence.entity.ProfileJpaEntity;
import com.latinhouse.backend.profile.domain.Profile;
import com.latinhouse.backend.profile.domain.Sex;
import com.latinhouse.backend.profile.port.in.request.CreateProfileAppRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileJpaMapper {

    public ProfileJpaEntity mapToJpaEntity(CreateProfileAppRequest req) {
        return ProfileJpaEntity.builder()
                .id(RandomStringUtils.random(8, true, true))
                .nickname(req.getNickname())
                .password(req.getPassword())
                .sex(req.getSex())
                .isInstructor(req.getIsInstructor())
                .build();
    }

    public ProfileJpaEntity mapToJpaEntity(Profile profile) {
        return ProfileJpaEntity.builder()
                .id(profile.getId())
                .nickname(profile.getNickname())
                .password(profile.getPassword())
                .sex(profile.getSex().getCode())
                .isInstructor(profile.getIsInstructor())
                .build();
    }

    public Profile mapToDomainEntity(ProfileJpaEntity profileT) {
        return Profile.builder()
                .id(profileT.getId())
                .nickname(profileT.getNickname())
                .password(profileT.getPassword())
                .sex(Sex.of(profileT.getSex()))
                .isInstructor(profileT.getIsInstructor())
                .build();
    }
}