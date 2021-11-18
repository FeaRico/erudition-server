package ru.mahach.eruditionserver.services;

import ru.mahach.eruditionserver.models.Question;

import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс сервиса {@link Question}
 * @author Makhach Abdulazizov
 * @version 1.0
 */
public interface QuestionService {

    Optional<Question> createQuestion(Question question);

    Optional<Question> updateQuestion(Question question);

    Optional<Question> deleteQuestionById(Long id);

    Optional<Question> questionFindById(Long id);

    Collection<Question> questionFindAll();

}
