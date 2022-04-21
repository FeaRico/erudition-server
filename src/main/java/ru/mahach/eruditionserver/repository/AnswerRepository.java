package ru.mahach.eruditionserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mahach.eruditionserver.models.entity.AnswerEntity;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
    @Query("select awr from AnswerEntity awr where awr.question.id = ?1")
    List<AnswerEntity> findAnswersByQuestionId(Long questionId);
}
