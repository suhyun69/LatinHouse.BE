package com.latinhouse.backend.profile.adapter.in.web;

import com.latinhouse.backend.profile.adapter.in.web.request.CreateProfileWebRequest;
import com.latinhouse.backend.profile.adapter.in.web.response.CreateProfileWebResponse;
import com.latinhouse.backend.profile.adapter.in.web.response.GetProfileWebResponse;
import com.latinhouse.backend.profile.port.in.CreateProfileUseCase;
import com.latinhouse.backend.profile.port.in.GetProfileUseCase;
import com.latinhouse.backend.profile.port.in.UpdateProfileUseCase;
import com.latinhouse.backend.profile.port.in.request.CreateProfileAppRequest;
import com.latinhouse.backend.profile.port.in.response.CreateProfileAppResponse;
import com.latinhouse.backend.profile.port.in.response.GetProfileAppResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
@Tag(name = "Profile", description = "Profile API Document")
public class ProfileController {

    private final CreateProfileUseCase createProfileUseCase;
    private final GetProfileUseCase getProfileUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;

    @PostMapping
    @Operation(summary = "프로필 등록", description = "프로필을 등록합니다")
    public ResponseEntity<CreateProfileWebResponse> createProfile(@RequestBody @Valid CreateProfileWebRequest webReq) {
        CreateProfileAppRequest appReq = new CreateProfileAppRequest(webReq);
        CreateProfileAppResponse appRes = createProfileUseCase.createProfile(appReq);
        return ResponseEntity.ok(new CreateProfileWebResponse(appRes));
    }

    // ID로 조회
    @GetMapping("/{id}")
    @Operation(summary = "프로필 조회", description = "프로필을 조회합니다")
    public ResponseEntity<GetProfileWebResponse> getProfileById(@PathVariable String id) {
        GetProfileAppResponse appRes = getProfileUseCase.getProfileById(id);
        return ResponseEntity.ok(new GetProfileWebResponse(appRes));
    }

    // 모든 프로필 조회
    @GetMapping
    @Operation(summary = "프로필 조회", description = "모든 프로필을 조회합니다")
    public ResponseEntity<List<GetProfileWebResponse>> getAllProfiles() {
        List<GetProfileAppResponse> appResList = getProfileUseCase.getAllProfiles();
        return ResponseEntity.ok(appResList.stream().map(GetProfileWebResponse::new).toList());
    }

    // 강사 등록
    @PutMapping("/{id}")
    @Operation(summary = "강사 등록", description = "강사로 등록합니다")
    public ResponseEntity<GetProfileWebResponse> updateIsInstructor(@PathVariable String id) {
        GetProfileAppResponse appRes = updateProfileUseCase.updateIsInstructor(id);
        return ResponseEntity.ok(new GetProfileWebResponse(appRes));
    }
}