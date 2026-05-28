package com.latinhouse.api.profile.adapter.in.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateProfileWebRequest {

    @NotBlank(message = "닉네임을 입력해 주세요.")
    private String nickname;

    @NotBlank(message = "성별을 입력해 주세요.")
    @Pattern(regexp = "^[MF]$", message = "성별은 M 또는 F만 입력 가능합니다.")
    private String sex;
}
