package com.latinhouse.api.profile.adapter.out.persistence;

import com.latinhouse.api.profile.domain.Profile;
import com.latinhouse.api.profile.domain.Sex;

class ProfilePersistenceMapper {

    private ProfilePersistenceMapper() {}

    static ProfileEntity toEntity(Profile profile) {
        return ProfileEntity.builder()
                .id(profile.getId())
                .nickname(profile.getNickname())
                .sex(profile.getSex().name())
                .isInstructor(profile.isInstructor())
                .build();
    }

    static Profile toDomain(ProfileEntity entity) {
        return Profile.builder()
                .id(entity.getId())
                .nickname(entity.getNickname())
                .sex(Sex.valueOf(entity.getSex()))
                .isInstructor(entity.isInstructor())
                .build();
    }
}
