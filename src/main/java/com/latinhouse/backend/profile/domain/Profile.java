package com.latinhouse.backend.profile.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private Long id;
    private String name;
    private String email;
}