package com.latinhouse.backend.profile.adapter.in.web.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateProfileWebRequest {

    @NotNull(message = "nickname cannot be null.")
    private String nickname;

    @NotNull(message = "password cannot be null")
    private String password;

    @NotNull(message = "sex cannot be null.")
    @Pattern(regexp = "[MF]", message = "sex should be 'M' or 'F'.")
    private String sex;
}
