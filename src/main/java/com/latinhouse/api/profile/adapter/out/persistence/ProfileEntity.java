package com.latinhouse.api.profile.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileEntity {

    @Id
    @Column(name = "id", length = 8, nullable = false)
    private String id;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "sex", nullable = false, length = 1)
    private String sex;

    @Column(name = "is_instructor", nullable = false)
    private boolean isInstructor;

    @Builder
    public ProfileEntity(String id, String nickname, String sex, boolean isInstructor) {
        this.id = id;
        this.nickname = nickname;
        this.sex = sex;
        this.isInstructor = isInstructor;
    }
}
