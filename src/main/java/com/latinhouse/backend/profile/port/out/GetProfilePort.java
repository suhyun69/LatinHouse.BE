package com.latinhouse.backend.profile.port.out;

import com.latinhouse.backend.profile.domain.Profile;
import com.latinhouse.backend.profile.port.in.response.GetProfileAppResponse;

import java.util.List;

public interface GetProfilePort {
    Profile getProfileById(String id);
    List<Profile> getAllProfiles();
}
