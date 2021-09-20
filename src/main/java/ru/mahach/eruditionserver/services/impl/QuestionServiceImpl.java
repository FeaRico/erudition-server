package ru.mahach.eruditionserver.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mahach.eruditionserver.exceptions.QuestionNotFoundException;
import ru.mahach.eruditionserver.models.Question;
import ru.mahach.eruditionserver.repository.QuestionRepository;
import ru.mahach.eruditionserver.services.QuestionService;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Optional<Question> createQuestion(Question question) {
        return Optional.of(questionRepository.save(question));
    }

    @Override
    public Optional<Question> updateQuestion(Question question) {
        Question questionToUpdate = questionRepository.findById(question.getId())
                .orElseThrow(() -> new QuestionNotFoundException(question.getId()));
        return Optional.of(questionRepository.save(questionToUpdate));
    }

    @Override
    public Optional<Question> deleteQuestionById(Long id) {
        Question questionToDelete = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        questionRepository.deleteById(id);
        return Optional.of(questionToDelete);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Question> questionFindById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Question> questionFindAll() {
        return questionRepository.findAll();
    }
}
