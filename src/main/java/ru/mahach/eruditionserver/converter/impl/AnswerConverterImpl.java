package ru.mahach.eruditionserver.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mahach.eruditionserver.converter.AnswerConverter;
import ru.mahach.eruditionserver.converter.QuestionConverter;
import ru.mahach.eruditionserver.models.dto.AnswerDto;
import ru.mahach.eruditionserver.models.entity.AnswerEntity;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AnswerConverterImpl implements AnswerConverter {
    private QuestionConverter questionConverter;

    Function<AnswerEntity, AnswerDto> entityToDto = entity -> new AnswerDto(
            entity.getId(), entity.getText(),
            entity.getIsTrue(), questionConverter.entityToDto(entity.getQuestion())
    );

    Function<AnswerDto, AnswerEntity> dtoToEntity = dto -> {
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setId(dto.getId());
        answerEntity.setText(dto.getText());
        answerEntity.setIsTrue(dto.getIsTrue());
        answerEntity.setQuestion(questionConverter.dtoToEntity(dto.getQuestion()));
        return answerEntity;
    };

    @Autowired
    public AnswerConverterImpl(QuestionConverter questionConverter) {
        this.questionConverter = questionConverter;
    }

    @Override
    public AnswerDto entityToDto(AnswerEntity answerEntity) {
        return entityToDto.apply(answerEntity);
    }

    @Override
    public List<AnswerDto> entityToDto(List<AnswerEntity> entities) {
        if (entities.isEmpty())
            return Collections.emptyList();

        return entities.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public AnswerEntity dtoToEntity(AnswerDto answerDto) {
        return dtoToEntity.apply(answerDto);
    }

    @Override
    public List<AnswerEntity> dtoToEntity(List<AnswerDto> dtos) {
        if (dtos.isEmpty())
            return Collections.emptyList();

        return dtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
