package com.latinhouse.backend.profile.application;

import com.latinhouse.backend.profile.adapter.out.persistence.ProfilePersistenceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProfileApplicationConfig {

    @Bean
    public ProfileService profileService(ProfilePersistenceAdapter profilePersistenceAdapter) {
        return new ProfileService(profilePersistenceAdapter);
    }
}