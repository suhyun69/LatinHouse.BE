package com.latinhouse.api.lesson.adapter.out.persistence;

import com.latinhouse.api.common.exception.LessonNotFoundException;
import com.latinhouse.api.lesson.application.port.out.LoadLessonPort;
import com.latinhouse.api.lesson.application.port.out.LoadLessonsPort;
import com.latinhouse.api.lesson.application.port.out.SaveLessonPort;
import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.domain.Region;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
class LessonPersistenceAdapter implements SaveLessonPort, LoadLessonPort, LoadLessonsPort {

    private final LessonJpaRepository lessonJpaRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public Lesson save(Lesson lesson) {
        if (lesson.getId() != null) {
            syncChildEntities(lesson);
            entityManager.flush();
            entityManager.clear();
        }
        LessonEntity entity = LessonPersistenceMapper.toEntity(lesson);
        LessonEntity saved = lessonJpaRepository.save(entity);
        return LessonPersistenceMapper.toDomain(saved);
    }

    private void syncChildEntities(Lesson lesson) {
        Long lessonId = lesson.getId();

        deleteRemovedChildren("lesson_option", lessonId,
                lesson.getOptions().stream().map(o -> o.getId()).filter(Objects::nonNull).toList());

        deleteRemovedChildren("lesson_discount", lessonId,
                lesson.getDiscounts().stream().map(d -> d.getId()).filter(Objects::nonNull).toList());

        deleteRemovedChildren("lesson_contact", lessonId,
                lesson.getContacts().stream().map(c -> c.getId()).filter(Objects::nonNull).toList());

        deleteRemovedChildren("lesson_notice", lessonId,
                lesson.getNotices().stream().map(n -> n.getId()).filter(Objects::nonNull).toList());

        syncAccountEntity(lessonId, lesson.getAccount() != null ? lesson.getAccount().getId() : null);
    }

    private void deleteRemovedChildren(String table, Long lessonId, List<Long> keepIds) {
        if (keepIds.isEmpty()) {
            entityManager.createNativeQuery("DELETE FROM " + table + " WHERE lesson_id = :lessonId")
                    .setParameter("lessonId", lessonId).executeUpdate();
        } else {
            String inClause = keepIds.stream().map(String::valueOf).collect(Collectors.joining(","));
            entityManager.createNativeQuery(
                    "DELETE FROM " + table + " WHERE lesson_id = :lessonId AND id NOT IN (" + inClause + ")")
                    .setParameter("lessonId", lessonId).executeUpdate();
        }
    }

    @SuppressWarnings("unchecked")
    private void syncAccountEntity(Long lessonId, Long newAccountId) {
        List<Object> rows = entityManager.createNativeQuery(
                "SELECT account_id FROM lesson WHERE id = :lessonId")
                .setParameter("lessonId", lessonId)
                .getResultList();
        Object oldAccountId = rows.isEmpty() ? null : rows.get(0);
        if (oldAccountId == null) return;

        boolean keepAccount = newAccountId != null && newAccountId.equals(((Number) oldAccountId).longValue());
        if (!keepAccount) {
            entityManager.createNativeQuery("UPDATE lesson SET account_id = NULL WHERE id = :lessonId")
                    .setParameter("lessonId", lessonId).executeUpdate();
            entityManager.createNativeQuery("DELETE FROM lesson_account WHERE id = :accountId")
                    .setParameter("accountId", oldAccountId).executeUpdate();
        }
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
