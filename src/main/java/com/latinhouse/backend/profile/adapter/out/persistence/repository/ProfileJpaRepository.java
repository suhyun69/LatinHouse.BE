package com.latinhouse.backend.profile.adapter.out.persistence.repository;

import com.latinhouse.backend.profile.adapter.out.persistence.entity.ProfileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileJpaRepository extends JpaRepository<ProfileJpaEntity, String> {
    Optional<ProfileJpaEntity> findByIdAndIsInstructorIsTrue(String profileId);
    List<ProfileJpaEntity> findByIsInstructor(Boolean isInstructor);
}