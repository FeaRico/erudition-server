package ru.mahach.eruditionserver.services;

import ru.mahach.eruditionserver.entity.QuestionEntity;

import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс сервиса {@link QuestionEntity}
 * @author Makhach Abdulazizov
 * @version 1.0
 */
public interface QuestionService {

    Optional<QuestionEntity> createQuestion(QuestionEntity question);

    Optional<QuestionEntity> updateQuestion(QuestionEntity question);

    Optional<QuestionEntity> deleteQuestionById(Long id);

    Optional<QuestionEntity> questionFindById(Long id);

    Collection<QuestionEntity> questionFindAll();

}
