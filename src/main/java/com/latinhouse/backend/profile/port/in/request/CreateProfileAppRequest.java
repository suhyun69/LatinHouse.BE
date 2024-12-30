package com.latinhouse.backend.profile.port.in.request;

import com.latinhouse.backend.common.SelfValidating;
import com.latinhouse.backend.profile.adapter.in.web.request.CreateProfileWebRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class CreateProfileAppRequest extends SelfValidating<CreateProfileAppRequest> {

    @NotNull(message = "nickname cannot be null.")
    private final String nickname;

    @NotNull(message = "password cannot be null.")
    private final String password;

    @NotNull(message = "sex cannot be null.")
    @Pattern(regexp = "[MF]", message = "sex should be 'M' or 'F'.")
    private final String sex;

    @NotNull(message = "isInstructor cannot be null.")
    private Boolean isInstructor;

    @Builder
    public CreateProfileAppRequest(CreateProfileWebRequest webReq) {
        this.nickname = webReq.getNickname();
        this.password = webReq.getPassword();
        this.sex = webReq.getSex();
        this.isInstructor = false;
        this.validateSelf();
    }
}
