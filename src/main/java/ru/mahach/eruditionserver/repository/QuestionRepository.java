package ru.mahach.eruditionserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mahach.eruditionserver.models.entity.QuestionEntity;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    @Query(value = "select quest from QuestionEntity quest where quest.item.id = ?1")
    List<QuestionEntity> findQuestionsByItemId(Long itemId);
}
