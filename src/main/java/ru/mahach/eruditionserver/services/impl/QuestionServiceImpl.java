package ru.mahach.eruditionserver.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mahach.eruditionserver.converter.QuestionConverter;
import ru.mahach.eruditionserver.exceptions.QuestionNotFoundException;
import ru.mahach.eruditionserver.models.dto.QuestionDto;
import ru.mahach.eruditionserver.models.entity.QuestionEntity;
import ru.mahach.eruditionserver.repository.QuestionRepository;
import ru.mahach.eruditionserver.services.QuestionService;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса {@link QuestionService}
 *
 * @author Makhach Abdulazizov
 * @version 1.0
 */
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionConverter questionConverter;

    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionConverter questionConverter) {
        this.questionRepository = questionRepository;
        this.questionConverter = questionConverter;
    }

    @Override
    public Optional<QuestionDto> createQuestion(QuestionDto question) {
        QuestionEntity saveEntity = questionRepository.save(questionConverter.dtoToEntity(question));

        return Optional.of(questionConverter.entityToDto(saveEntity));
    }

    @Override
    public Optional<QuestionDto> updateQuestion(QuestionDto question) {
        questionRepository.findById(question.getId())
                .orElseThrow(() -> new QuestionNotFoundException(question.getId()));
        QuestionEntity questionEntity = questionConverter.dtoToEntity(question);

        return Optional.of(questionConverter.entityToDto(questionRepository.save(questionEntity)));
    }

    @Override
    public Optional<QuestionDto> deleteQuestionById(Long id) {
        QuestionEntity questionToDelete = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        questionRepository.deleteById(id);

        return Optional.of(questionConverter.entityToDto(questionToDelete));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionDto> getQuestionById(Long id) {
        QuestionEntity findQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        return Optional.of(questionConverter.entityToDto(findQuestion));
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> getAllQuestions() {
        List<QuestionEntity> findQuestions = questionRepository.findAll();

        return questionConverter.entityToDto(findQuestions);
    }

    @Override
    public List<QuestionDto> getAllQuestionsByItemId(Long itemId) {
        return questionConverter.entityToDto(questionRepository.findQuestionsByItemId(itemId));
    }
}
