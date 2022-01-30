package ru.mahach.eruditionserver.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mahach.eruditionserver.exceptions.QuestionNotFoundException;
import ru.mahach.eruditionserver.entity.QuestionEntity;
import ru.mahach.eruditionserver.repository.QuestionRepository;
import ru.mahach.eruditionserver.services.QuestionService;

import java.util.Collection;
import java.util.Optional;

/**
 * Реализация сервиса {@link QuestionService}
 * @author Makhach Abdulazizov
 * @version 1.0
 */
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Optional<QuestionEntity> createQuestion(QuestionEntity question) {
        return Optional.of(questionRepository.save(question));
    }

    @Override
    public Optional<QuestionEntity> updateQuestion(QuestionEntity question) {
        QuestionEntity questionToUpdate = questionRepository.findById(question.getId())
                .orElseThrow(() -> new QuestionNotFoundException(question.getId()));
        questionToUpdate.setText(question.getText());
        questionToUpdate.setItemId(question.getItemId());
        questionToUpdate.setImagePath(question.getImagePath());
        return Optional.of(questionRepository.save(questionToUpdate));
    }

    @Override
    public Optional<QuestionEntity> deleteQuestionById(Long id) {
        QuestionEntity questionToDelete = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        questionRepository.deleteById(id);
        return Optional.of(questionToDelete);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionEntity> questionFindById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<QuestionEntity> questionFindAll() {
        return questionRepository.findAll();
    }
}
