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
    Optional<QuestionDto> create(QuestionDto question);

    Optional<QuestionDto> update(QuestionDto question);

    Optional<QuestionDto> deleteById(Long id);

    Optional<QuestionDto> getById(Long id);

    List<QuestionDto> getAll();
}
