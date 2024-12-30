package com.latinhouse.backend.profile.adapter.in.web;

import com.latinhouse.backend.profile.adapter.in.web.request.CreateProfileWebRequest;
import com.latinhouse.backend.profile.adapter.in.web.response.CreateProfileWebResponse;
import com.latinhouse.backend.profile.adapter.in.web.response.GetProfileWebResponse;
import com.latinhouse.backend.profile.port.in.CreateProfileUseCase;
import com.latinhouse.backend.profile.port.in.GetProfileUseCase;
import com.latinhouse.backend.profile.port.in.request.CreateProfileAppRequest;
import com.latinhouse.backend.profile.port.in.response.CreateProfileAppResponse;
import com.latinhouse.backend.profile.port.in.response.GetProfileAppResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final CreateProfileUseCase createProfileUseCase;
    private final GetProfileUseCase getProfileUseCase;

    @PostMapping
    public ResponseEntity<CreateProfileWebResponse> createProfile(@RequestBody @Valid CreateProfileWebRequest webReq) {
        CreateProfileAppRequest appReq = new CreateProfileAppRequest(webReq);
        CreateProfileAppResponse appRes = createProfileUseCase.createProfile(appReq);
        return ResponseEntity.ok(new CreateProfileWebResponse(appRes));
    }

    // ID로 조회
    @GetMapping("/{id}")
    public ResponseEntity<GetProfileWebResponse> getProfileById(@PathVariable String id) {
        GetProfileAppResponse appRes = getProfileUseCase.getProfileById(id);
        return ResponseEntity.ok(new GetProfileWebResponse(appRes));
    }

    // 모든 프로필 조회
    @GetMapping
    public ResponseEntity<List<GetProfileWebResponse>> getAllProfiles() {
        List<GetProfileAppResponse> appResList = getProfileUseCase.getAllProfiles();
        return ResponseEntity.ok(appResList.stream().map(GetProfileWebResponse::new).toList());
    }
}