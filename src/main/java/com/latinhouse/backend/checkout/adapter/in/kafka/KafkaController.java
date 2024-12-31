package com.latinhouse.backend.checkout.adapter.in.kafka;

import com.latinhouse.backend.checkout.port.in.EnrollLessonUseCase;
import com.latinhouse.backend.checkout.port.in.request.EnrollLessonAppRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaController {

    private final EnrollLessonUseCase enrollLessonUseCase;

    @KafkaListener(topics = "test-topic", groupId = "your-group-id")
    public void consume(EnrollLessonAppRequest appReq) {
        try {
            // Kafka 메시지를 도메인 계층에 전달
            enrollLessonUseCase.enrollLesson(appReq);
            System.out.println("Message processed successfully: " + appReq.toString());
        } catch (Exception ex) {
            // 에러 로깅 및 필요 시 재시도 로직
            System.err.println("Error processing message: " + ex.getMessage());
        }
    }
}