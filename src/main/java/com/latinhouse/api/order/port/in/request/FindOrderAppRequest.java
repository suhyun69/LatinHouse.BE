package com.latinhouse.api.order.port.in.request;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FindOrderAppRequest {

    String profileId;
    Long lessonNo;
    String instructorLo;
    String instructorLa;

    public static FindOrderAppRequest of(String profileId, Long lessonNo,
                                         String instructorLo, String instructorLa) {
        return FindOrderAppRequest.builder()
                .profileId(profileId)
                .lessonNo(lessonNo)
                .instructorLo(instructorLo)
                .instructorLa(instructorLa)
                .build();
    }
}
