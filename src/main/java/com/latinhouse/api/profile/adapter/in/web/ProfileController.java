package com.latinhouse.api.profile.adapter.in.web;

import com.latinhouse.api.profile.application.port.in.CreateProfileUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Profile", description = "프로필 관리 API")
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final CreateProfileUseCase createProfileUseCase;

    @Operation(summary = "프로필 생성", description = "닉네임과 성별을 입력받아 프로필을 생성합니다.")
    @PostMapping
    public ResponseEntity<CreateProfileWebResponse> createProfile(
            @Valid @RequestBody CreateProfileWebRequest request) {
        CreateProfileWebResponse response = CreateProfileWebMapper.toWebResponse(
                createProfileUseCase.createProfile(
                        CreateProfileWebMapper.toAppRequest(request)
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
