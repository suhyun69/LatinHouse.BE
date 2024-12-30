package com.latinhouse.backend.lesson.adapter.in.web;

import com.latinhouse.backend.lesson.adapter.in.web.request.CreateLessonWebRequest;
import com.latinhouse.backend.lesson.adapter.in.web.response.CreateLessonWebResponse;
import com.latinhouse.backend.lesson.adapter.in.web.response.GetLessonWebResponse;
import com.latinhouse.backend.lesson.port.in.CreateLessonUseCase;
import com.latinhouse.backend.lesson.port.in.GetLessonUseCase;
import com.latinhouse.backend.lesson.port.in.request.CreateLessonAppRequest;
import com.latinhouse.backend.lesson.port.in.response.CreateLessonAppResponse;
import com.latinhouse.backend.lesson.port.in.response.GetLessonAppResponse;
import com.latinhouse.backend.lesson.port.in.response.LessonInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final CreateLessonUseCase createLessonUseCase;
    private final GetLessonUseCase getLessonUseCase;

    @PostMapping
    public ResponseEntity<CreateLessonWebResponse> createLesson(@RequestBody @Valid CreateLessonWebRequest webReq) {
        CreateLessonAppRequest appReq = new CreateLessonAppRequest(webReq);
        CreateLessonAppResponse appRes = createLessonUseCase.createLesson(appReq);
        return ResponseEntity.ok(new CreateLessonWebResponse(appRes));
    }

    @GetMapping("/{no}")
    public ResponseEntity<LessonInfo> getLessonByNo(@PathVariable Long no) {
        LessonInfo lessonInfo = getLessonUseCase.getLessonByNo(no);
        return ResponseEntity.ok(lessonInfo);
    }
}