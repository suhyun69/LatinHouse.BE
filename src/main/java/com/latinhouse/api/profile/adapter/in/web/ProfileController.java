package com.latinhouse.api.profile.adapter.in.web;

import com.latinhouse.api.common.exception.InvalidParamException;
import com.latinhouse.api.profile.application.port.in.CreateProfileUseCase;
import com.latinhouse.api.profile.application.port.in.GetProfilesUseCase;
import com.latinhouse.api.profile.application.port.in.SetInstructorUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Profile", description = "프로필 관리 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

    private final CreateProfileUseCase createProfileUseCase;
    private final SetInstructorUseCase setInstructorUseCase;
    private final GetProfilesUseCase getProfilesUseCase;

    @Operation(summary = "프로필 생성", description = "닉네임과 성별을 입력받아 프로필을 생성합니다.")
    @PostMapping("/profile")
    public ResponseEntity<CreateProfileWebResponse> createProfile(
            @Valid @RequestBody CreateProfileWebRequest request) {
        CreateProfileWebResponse response = CreateProfileWebMapper.toWebResponse(
                createProfileUseCase.createProfile(
                        CreateProfileWebMapper.toAppRequest(request)
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "강사 지정", description = "프로필을 강사로 지정합니다.")
    @PatchMapping("/profile/{profileId}/instructor")
    public ResponseEntity<SetInstructorWebResponse> setInstructor(
            @PathVariable String profileId) {
        SetInstructorWebResponse response = SetInstructorWebMapper.toWebResponse(
                setInstructorUseCase.setInstructor(
                        SetInstructorWebMapper.toAppRequest(profileId)
                )
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "프로필 목록 조회", description = "프로필 목록을 조회합니다. isInstructor 파라미터로 필터링할 수 있습니다.")
    @GetMapping("/profiles")
    public ResponseEntity<List<GetProfilesWebResponse>> getProfiles(
            @RequestParam(required = false) String isInstructor) {
        Boolean filter = parseIsInstructor(isInstructor);
        List<GetProfilesWebResponse> response = getProfilesUseCase.getProfiles(filter).stream()
                .map(GetProfilesWebMapper::toWebResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    private Boolean parseIsInstructor(String value) {
        if (value == null) return null;
        if ("true".equalsIgnoreCase(value)) return true;
        if ("false".equalsIgnoreCase(value)) return false;
        throw new InvalidParamException("isInstructor", "isInstructor는 true 또는 false만 입력 가능합니다.");
    }
}
