package com.latinhouse.api.lesson.application.service;

import com.latinhouse.api.lesson.application.port.in.CreateLessonUseCase;
import com.latinhouse.api.lesson.application.port.in.CreateRandomLessonAppMapper;
import com.latinhouse.api.lesson.application.port.in.CreateRandomLessonAppResponse;
import com.latinhouse.api.lesson.application.port.in.CreateRandomLessonUseCase;
import com.latinhouse.api.profile.application.port.in.CreateProfileAppRequest;
import com.latinhouse.api.profile.application.port.in.CreateProfileUseCase;
import com.latinhouse.api.profile.application.port.in.GetProfilesAppResponse;
import com.latinhouse.api.profile.application.port.in.GetProfilesUseCase;
import com.latinhouse.api.profile.application.port.in.SetInstructorAppRequest;
import com.latinhouse.api.profile.application.port.in.SetInstructorUseCase;
import com.latinhouse.api.profile.domain.Sex;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class CreateRandomLessonService implements CreateRandomLessonUseCase {

    private final GetProfilesUseCase getProfilesUseCase;
    private final CreateProfileUseCase createProfileUseCase;
    private final SetInstructorUseCase setInstructorUseCase;
    private final CreateLessonUseCase createLessonUseCase;

    @Override
    public CreateRandomLessonAppResponse createRandomLesson() {
        List<GetProfilesAppResponse> instructors = getProfilesUseCase.getProfiles(true);

        List<GetProfilesAppResponse> maleInstructors = instructors.stream()
                .filter(p -> p.getSex() == Sex.M)
                .toList();
        List<GetProfilesAppResponse> femaleInstructors = instructors.stream()
                .filter(p -> p.getSex() == Sex.F)
                .toList();

        String instructorLoId = maleInstructors.isEmpty()
                ? createInstructor(Sex.M)
                : randomPick(maleInstructors).getId();

        String instructorLaId = femaleInstructors.isEmpty()
                ? createInstructor(Sex.F)
                : randomPick(femaleInstructors).getId();

        return CreateRandomLessonAppMapper.toAppResponse(
                createLessonUseCase.createLesson(
                        CreateRandomLessonAppMapper.toCreateLessonAppRequest(instructorLoId, instructorLaId)
                )
        );
    }

    private String createInstructor(Sex sex) {
        int suffix = ThreadLocalRandom.current().nextInt(1000, 10000);
        String nickname = (sex == Sex.M ? "강사_M_" : "강사_F_") + suffix;

        String profileId = createProfileUseCase.createProfile(
                CreateProfileAppRequest.builder()
                        .nickname(nickname)
                        .sex(sex)
                        .build()
        ).getId();

        setInstructorUseCase.setInstructor(
                SetInstructorAppRequest.builder()
                        .profileId(profileId)
                        .build()
        );

        return profileId;
    }

    private GetProfilesAppResponse randomPick(List<GetProfilesAppResponse> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }
}
