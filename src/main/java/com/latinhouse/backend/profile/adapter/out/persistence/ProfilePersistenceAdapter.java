package com.latinhouse.backend.profile.adapter.out.persistence;

import com.latinhouse.backend.profile.domain.Profile;
import com.latinhouse.backend.profile.port.out.SaveProfilePort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProfilePersistenceAdapter implements SaveProfilePort {

    private final Map<Long, Profile> database = new HashMap<>();
    private Long idCounter = 1L; // 간단한 ID 생성

    @Override
    public Profile saveProfile(Profile profile) {
        profile.setId(idCounter++);
        database.put(profile.getId(), profile);
        return profile;
    }
}