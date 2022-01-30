package ru.mahach.eruditionserver.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mahach.eruditionserver.exceptions.AnswerNotFoundException;
import ru.mahach.eruditionserver.entity.AnswerEntity;
import ru.mahach.eruditionserver.repository.AnswerRepository;
import ru.mahach.eruditionserver.services.AnswerService;

import java.util.Collection;
import java.util.Optional;

/**
 * Реализация сервиса {@link AnswerService}
 * @author Makhach Abdulazizov
 * @version 1.0
 */

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public Optional<AnswerEntity> createAnswer(AnswerEntity answer) {
        return Optional.of(answerRepository.save(answer));
    }

    @Override
    public Optional<AnswerEntity> updateAnswer(AnswerEntity answer) {
        AnswerEntity answerToUpdate = answerRepository.findById(answer.getId())
                .orElseThrow(() -> new AnswerNotFoundException(answer.getId()));
        answerToUpdate.setText(answer.getText());
        answerToUpdate.setQuestionId(answer.getQuestionId());
        answerToUpdate.setTrue(answer.isTrue());
        return Optional.of(answerRepository.save(answerToUpdate));
    }

    @Override
    public Optional<AnswerEntity> deleteAnswerById(Long id) {
        AnswerEntity answerToDelete = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException(id));
        answerRepository.deleteById(id);
        return Optional.of(answerToDelete);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnswerEntity> answerFindById(Long id) {
        return answerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<AnswerEntity> answerFindAll() {
        return answerRepository.findAll();
    }
}
