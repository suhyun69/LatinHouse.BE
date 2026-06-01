package com.latinhouse.api.lesson.adapter.in.web;

import com.latinhouse.api.lesson.application.port.in.CreateLessonUseCase;
import com.latinhouse.api.lesson.application.port.in.GetLessonUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Lesson", description = "레슨 관리 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LessonController {

    private final CreateLessonUseCase createLessonUseCase;
    private final GetLessonUseCase getLessonUseCase;

    @Operation(summary = "레슨 생성", description = "레슨 정보를 입력받아 레슨을 생성합니다.")
    @PostMapping("/lesson")
    public ResponseEntity<CreateLessonWebResponse> createLesson(
            @Valid @RequestBody CreateLessonWebRequest request) {
        CreateLessonWebResponse response = CreateLessonWebMapper.toWebResponse(
                createLessonUseCase.createLesson(
                        CreateLessonWebMapper.toAppRequest(request)
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "레슨 단건 조회", description = "레슨 ID로 레슨 상세 정보를 조회합니다.")
    @GetMapping("/lessons/{lessonNo}")
    public ResponseEntity<GetLessonWebResponse> getLesson(@PathVariable Long lessonNo) {
        GetLessonWebResponse response = GetLessonWebMapper.toWebResponse(
                getLessonUseCase.getLesson(lessonNo)
        );
        return ResponseEntity.ok(response);
    }
}
