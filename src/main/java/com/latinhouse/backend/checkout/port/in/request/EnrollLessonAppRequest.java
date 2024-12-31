package com.latinhouse.backend.checkout.port.in.request;

import com.latinhouse.backend.checkout.adapter.in.web.request.EnrollLessonWebRequest;
import com.latinhouse.backend.common.SelfValidating;
import com.latinhouse.backend.profile.adapter.in.web.request.CreateProfileWebRequest;
import com.latinhouse.backend.profile.port.in.request.CreateProfileAppRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class EnrollLessonAppRequest extends SelfValidating<EnrollLessonAppRequest> {

    private Long lessonNo;
    private String profileId;

    @Builder
    public EnrollLessonAppRequest(EnrollLessonWebRequest webReq) {
        this.lessonNo = webReq.getLessonNo();
        this.profileId = webReq.getProfileId();
        this.validateSelf();
    }
}
