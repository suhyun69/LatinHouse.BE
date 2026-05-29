package com.latinhouse.api.profile.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileJpaRepository extends JpaRepository<ProfileEntity, String> {
}
