package ru.mahach.eruditionserver.services;

import ru.mahach.eruditionserver.models.dto.AnswerDto;
import ru.mahach.eruditionserver.models.entity.AnswerEntity;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс сервиса сущности {@link AnswerEntity}
 *
 * @author Makhach Abdulazizov
 * @version 1.0
 */

public interface AnswerService {
    Optional<AnswerDto> createAnswer(AnswerDto answer);

    Optional<AnswerDto> updateAnswer(AnswerDto answer);

    Optional<AnswerDto> deleteAnswerById(Long id);

    Optional<AnswerDto> getAnswerById(Long id);

    List<AnswerDto> getAllAnswers();
}
