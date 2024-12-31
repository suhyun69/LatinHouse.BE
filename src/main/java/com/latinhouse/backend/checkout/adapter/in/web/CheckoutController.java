package com.latinhouse.backend.checkout.adapter.in.web;

import com.latinhouse.backend.checkout.adapter.in.web.request.EnrollLessonWebRequest;
import com.latinhouse.backend.checkout.adapter.in.web.response.EnrollLessonWebResponse;
import com.latinhouse.backend.checkout.port.in.EnrollLessonUseCase;
import com.latinhouse.backend.checkout.port.in.request.EnrollLessonAppRequest;
import com.latinhouse.backend.checkout.port.in.response.EnrollLessonAppResponse;
import com.latinhouse.backend.profile.adapter.in.web.request.CreateProfileWebRequest;
import com.latinhouse.backend.profile.adapter.in.web.response.CreateProfileWebResponse;
import com.latinhouse.backend.profile.port.in.request.CreateProfileAppRequest;
import com.latinhouse.backend.profile.port.in.response.CreateProfileAppResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
@Tag(name = "Checkout", description = "Checkout API Document")
public class CheckoutController {

    private final EnrollLessonUseCase enrollLessonUseCase;

    @PostMapping
    @Operation(summary = "수업 신청", description = "수업을 신청합니다")
    public ResponseEntity<String> enrollLesson(@RequestBody @Valid EnrollLessonWebRequest webReq) {
        EnrollLessonAppRequest appReq = new EnrollLessonAppRequest(webReq);
        enrollLessonUseCase.enqueueKafka(appReq);
        return ResponseEntity.ok("Success");
    }
}
