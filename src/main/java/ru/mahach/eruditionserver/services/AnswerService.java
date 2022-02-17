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
    Optional<AnswerDto> create(AnswerDto answer);

    Optional<AnswerDto> update(AnswerDto answer);

    Optional<AnswerDto> deleteById(Long id);

    Optional<AnswerDto> getById(Long id);

    List<AnswerDto> getAll();
}
