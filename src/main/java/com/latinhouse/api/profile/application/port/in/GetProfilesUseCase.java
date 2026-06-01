package com.latinhouse.api.profile.application.port.in;

import java.util.List;

public interface GetProfilesUseCase {

    List<GetProfilesAppResponse> getProfiles(Boolean isInstructor);
}
