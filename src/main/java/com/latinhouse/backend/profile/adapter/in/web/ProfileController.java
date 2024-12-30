package com.latinhouse.backend.profile.adapter.in.web;

import com.latinhouse.backend.profile.domain.Profile;
import com.latinhouse.backend.profile.port.in.CreateProfileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final CreateProfileUseCase createProfileUseCase;

    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        Profile savedProfile = createProfileUseCase.createProfile(profile);
        return ResponseEntity.ok(savedProfile);
    }
}