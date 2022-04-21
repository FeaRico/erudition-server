package ru.mahach.eruditionserver.services;

import ru.mahach.eruditionserver.models.dto.QuestionDto;
import ru.mahach.eruditionserver.models.entity.QuestionEntity;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс сервиса {@link QuestionEntity}
 *
 * @author Makhach Abdulazizov
 * @version 1.0
 */
public interface QuestionService {
    Optional<QuestionDto> createQuestion(QuestionDto question);

    Optional<QuestionDto> updateQuestion(QuestionDto question);

    Optional<QuestionDto> deleteQuestionById(Long id);

    Optional<QuestionDto> getQuestionById(Long id);

    List<QuestionDto> getAllQuestions();

    List<QuestionDto> getAllQuestionsByItemId(Long itemId);
}
