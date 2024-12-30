package com.latinhouse.backend.profile.adapter.in.web;

import com.latinhouse.backend.profile.adapter.in.web.request.CreateProfileWebRequest;
import com.latinhouse.backend.profile.adapter.in.web.response.CreateProfileWebResponse;
import com.latinhouse.backend.profile.domain.Profile;
import com.latinhouse.backend.profile.port.in.CreateProfileUseCase;
import com.latinhouse.backend.profile.port.in.request.CreateProfileAppRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final CreateProfileUseCase createProfileUseCase;

    @PostMapping
    public ResponseEntity<CreateProfileWebResponse> createProfile(@RequestBody @Valid CreateProfileWebRequest webReq) {

        CreateProfileAppRequest appReq = new CreateProfileAppRequest(webReq);
        CreateProfileWebResponse webRes = new CreateProfileWebResponse(createProfileUseCase.createProfile(appReq));

        return ResponseEntity.ok(webRes);
    }
}