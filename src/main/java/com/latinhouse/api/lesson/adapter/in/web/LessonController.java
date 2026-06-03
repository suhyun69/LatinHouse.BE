package com.latinhouse.api.lesson.adapter.in.web;

import com.latinhouse.api.lesson.application.port.in.CreateLessonUseCase;
import com.latinhouse.api.lesson.application.port.in.CreateRandomLessonUseCase;
import com.latinhouse.api.lesson.application.port.in.GetLessonUseCase;
import com.latinhouse.api.lesson.application.port.in.GetLessonsUseCase;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Lesson", description = "레슨 관리 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LessonController {

    private final CreateLessonUseCase createLessonUseCase;
    private final CreateRandomLessonUseCase createRandomLessonUseCase;
    private final GetLessonUseCase getLessonUseCase;
    private final GetLessonsUseCase getLessonsUseCase;

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

    @Operation(summary = "랜덤 수업 생성", description = "파라미터를 랜덤으로 생성하여 수업을 생성합니다.")
    @PostMapping("/lesson/random")
    public ResponseEntity<CreateRandomLessonWebResponse> createRandomLesson() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CreateRandomLessonWebMapper.toWebResponse(
                        createRandomLessonUseCase.createRandomLesson()
                ));
    }

    @Operation(summary = "레슨 목록 조회", description = "레슨 옵션 단위 목록을 조회합니다. region, instructor, genre 필터를 선택적으로 적용합니다.")
    @GetMapping("/lessons")
    public ResponseEntity<List<GetLessonsWebResponse>> getLessons(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String instructor,
            @RequestParam(required = false) String genre) {
        List<GetLessonsWebResponse> response = GetLessonsWebMapper.toWebResponseList(
                getLessonsUseCase.getLessons(
                        GetLessonsWebMapper.toAppRequest(region, instructor, genre)
                )
        );
        return ResponseEntity.ok(response);
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
