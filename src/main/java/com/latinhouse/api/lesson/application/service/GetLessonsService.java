package com.latinhouse.api.lesson.application.service;

import com.latinhouse.api.lesson.application.port.in.GetLessonsAppMapper;
import com.latinhouse.api.lesson.application.port.in.GetLessonsAppRequest;
import com.latinhouse.api.lesson.application.port.in.GetLessonsAppResponse;
import com.latinhouse.api.lesson.application.port.in.GetLessonsUseCase;
import com.latinhouse.api.lesson.application.port.out.LoadLessonsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetLessonsService implements GetLessonsUseCase {

    private final LoadLessonsPort loadLessonsPort;

    @Override
    public List<GetLessonsAppResponse> getLessons(GetLessonsAppRequest request) {
        return GetLessonsAppMapper.toResponseList(
                loadLessonsPort.loadLessons(request.getRegion(), request.getInstructor(), request.getGenre())
        );
    }
}
