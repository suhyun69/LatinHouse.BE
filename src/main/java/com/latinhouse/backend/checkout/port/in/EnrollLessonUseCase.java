package com.latinhouse.backend.checkout.port.in;

import com.latinhouse.backend.checkout.port.in.request.EnrollLessonAppRequest;
import com.latinhouse.backend.checkout.port.in.response.EnrollLessonAppResponse;

public interface EnrollLessonUseCase {
    void enqueueKafka(EnrollLessonAppRequest appReq);
    void enrollLesson(EnrollLessonAppRequest appReq);
}
