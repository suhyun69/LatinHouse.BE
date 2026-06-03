package com.latinhouse.api.lesson.adapter.out.persistence;

import com.latinhouse.api.common.exception.LessonNotFoundException;
import com.latinhouse.api.lesson.application.port.out.LoadLessonPort;
import com.latinhouse.api.lesson.application.port.out.LoadLessonsPort;
import com.latinhouse.api.lesson.application.port.out.SaveLessonPort;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.domain.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
class LessonPersistenceAdapter implements SaveLessonPort, LoadLessonPort, LoadLessonsPort {

    private final LessonJpaRepository lessonJpaRepository;

    @Override
    public Lesson save(Lesson lesson) {
        LessonEntity entity = LessonPersistenceMapper.toEntity(lesson);
        LessonEntity saved = lessonJpaRepository.save(entity);
        return LessonPersistenceMapper.toDomain(saved);
    }

    @Override
    public Lesson loadLesson(Long lessonNo) {
        LessonEntity entity = lessonJpaRepository.findById(lessonNo)
                .orElseThrow(() -> new LessonNotFoundException(lessonNo));
        return LessonPersistenceMapper.toDomain(entity);
    }

    @Override
    public List<Lesson> loadLessons(Region region, String instructor, Genre genre) {
        Specification<LessonEntity> spec = buildSpec(region, instructor, genre);
        return lessonJpaRepository.findAll(spec).stream()
                .map(LessonPersistenceMapper::toDomain)
                .toList();
    }

    private Specification<LessonEntity> buildSpec(Region region, String instructor, Genre genre) {
        Specification<LessonEntity> spec = (root, query, cb) -> cb.conjunction();

        if (genre != null) {
            final Genre g = genre;
            spec = spec.and((root, query, cb) -> cb.equal(root.get("genre"), g.getCode()));
        }
        if (instructor != null) {
            final String inst = instructor;
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.equal(root.get("instructorLo"), inst),
                    cb.equal(root.get("instructorLa"), inst)
            ));
        }
        if (region != null) {
            final Region r = region;
            spec = spec.and((root, query, cb) -> {
                query.distinct(true);
                var join = root.join("options");
                return cb.equal(join.get("region"), r.getCode());
            });
        }
        return spec;
    }
}
