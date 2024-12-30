package com.latinhouse.backend.lesson.adapter.out.persistence.service;

import com.latinhouse.backend.lesson.adapter.out.persistence.entity.LessonSequence;
import com.latinhouse.backend.lesson.adapter.out.persistence.repository.LessonSequenceMongoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonSequenceService {

    private final LessonSequenceMongoRepository lessonSequenceMongoRepository;

    @Transactional
    public long getNextSequence(String sequenceName) {
        LessonSequence lessonSequence = lessonSequenceMongoRepository.findById(sequenceName).orElseGet(() -> {
            LessonSequence newSequence = new LessonSequence();
            newSequence.setId(sequenceName);
            newSequence.setSequence(1);
            return lessonSequenceMongoRepository.save(newSequence);
        });

        long sequence = lessonSequence.getSequence();
        lessonSequence.setSequence(sequence + 1);
        lessonSequenceMongoRepository.save(lessonSequence);

        return sequence;
    }
}