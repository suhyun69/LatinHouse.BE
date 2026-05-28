package com.latinhouse.api.profile.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface ProfileJpaRepository extends JpaRepository<ProfileEntity, String> {
}
