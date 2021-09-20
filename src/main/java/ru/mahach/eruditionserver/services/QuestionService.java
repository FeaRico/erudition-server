package ru.mahach.eruditionserver.services;

import ru.mahach.eruditionserver.models.Question;

import java.util.Collection;
import java.util.Optional;

public interface QuestionService {

    Optional<Question> createQuestion(Question question);

    Optional<Question> updateQuestion(Question question);

    Optional<Question> deleteQuestionById(Long id);

    Optional<Question> questionFindById(Long id);

    Collection<Question> questionFindAll();

}
