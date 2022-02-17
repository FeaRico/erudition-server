package ru.mahach.eruditionserver.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mahach.eruditionserver.converter.AnswerConverter;
import ru.mahach.eruditionserver.exceptions.AnswerNotFoundException;
import ru.mahach.eruditionserver.models.dto.AnswerDto;
import ru.mahach.eruditionserver.models.entity.AnswerEntity;
import ru.mahach.eruditionserver.repository.AnswerRepository;
import ru.mahach.eruditionserver.services.AnswerService;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса {@link AnswerService}
 *
 * @author Makhach Abdulazizov
 * @version 1.0
 */

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final AnswerConverter answerConverter;

    public AnswerServiceImpl(AnswerRepository answerRepository, AnswerConverter answerConverter) {
        this.answerRepository = answerRepository;
        this.answerConverter = answerConverter;
    }

    @Override
    public Optional<AnswerDto> create(AnswerDto answer) {
        AnswerEntity answerEntity = answerConverter.dtoToEntity(answer);

        return Optional.of(answerConverter.entityToDto(answerRepository.save(answerEntity)));

    }

    @Override
    public Optional<AnswerDto> update(AnswerDto answer) {
        answerRepository.findById(answer.getId())
                .orElseThrow(() -> new AnswerNotFoundException(answer.getId()));

        AnswerEntity answerEntity = answerConverter.dtoToEntity(answer);

        return Optional.of(answerConverter.entityToDto(answerRepository.save(answerEntity)));
    }

    @Override
    public Optional<AnswerDto> deleteById(Long id) {
        AnswerEntity answerToDelete = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException(id));
        answerRepository.deleteById(id);

        return Optional.of(answerConverter.entityToDto(answerToDelete));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnswerDto> getById(Long id) {
        AnswerEntity findAnswer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException(id));
        return Optional.of(answerConverter.entityToDto(findAnswer));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnswerDto> getAll() {
        List<AnswerEntity> findAnswers = answerRepository.findAll();
        return answerConverter.entityToDto(findAnswers);
    }
}
