package ru.mahach.eruditionserver.services;

import ru.mahach.eruditionserver.models.Answer;

import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс сервиса сущности {@link Answer}
 * @author Makhach Abdulazizov
 * @version 1.0
 */

public interface AnswerService {

    Optional<Answer> createAnswer(Answer answer);

    Optional<Answer> updateAnswer(Answer answer);

    Optional<Answer> deleteAnswerById(Long id);

    Optional<Answer> answerFindById(Long id);

    Collection<Answer> answerFindAll();

}
