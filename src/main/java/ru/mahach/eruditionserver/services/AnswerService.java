package ru.mahach.eruditionserver.services;

import ru.mahach.eruditionserver.models.entity.AnswerEntity;

import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс сервиса сущности {@link AnswerEntity}
 * @author Makhach Abdulazizov
 * @version 1.0
 */

public interface AnswerService {

    Optional<AnswerEntity> createAnswer(AnswerEntity answer);

    Optional<AnswerEntity> updateAnswer(AnswerEntity answer);

    Optional<AnswerEntity> deleteAnswerById(Long id);

    Optional<AnswerEntity> answerFindById(Long id);

    Collection<AnswerEntity> answerFindAll();

}
