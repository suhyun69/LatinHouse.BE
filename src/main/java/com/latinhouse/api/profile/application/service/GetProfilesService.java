package com.latinhouse.api.profile.application.service;

import com.latinhouse.api.profile.application.port.in.GetProfilesAppMapper;
import com.latinhouse.api.profile.application.port.in.GetProfilesAppResponse;
import com.latinhouse.api.profile.application.port.in.GetProfilesUseCase;
import com.latinhouse.api.profile.application.port.out.FindProfilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetProfilesService implements GetProfilesUseCase {

    private final FindProfilePort findProfilePort;

    @Override
    public List<GetProfilesAppResponse> getProfiles(Boolean isInstructor) {
        return findProfilePort.findAll(isInstructor).stream()
                .map(GetProfilesAppMapper::toAppResponse)
                .toList();
    }
}
