package com.latinhouse.api.profile.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileJpaRepository extends JpaRepository<ProfileEntity, String> {

    List<ProfileEntity> findAllByIsInstructor(boolean isInstructor);
}
