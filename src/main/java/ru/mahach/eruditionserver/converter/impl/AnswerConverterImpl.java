package ru.mahach.eruditionserver.converter.impl;

import org.springframework.stereotype.Component;
import ru.mahach.eruditionserver.converter.AnswerConverter;
import ru.mahach.eruditionserver.dto.AnswerDto;
import ru.mahach.eruditionserver.entity.AnswerEntity;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AnswerConverterImpl implements AnswerConverter {
    Function<AnswerEntity, AnswerDto> entityToDto = entity -> new AnswerDto(
            entity.getId(), entity.getText(), entity.getQuestionId(), entity.isTrue()
    );

    Function<AnswerDto, AnswerEntity> dtoToEntity = dto -> {
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setId(dto.getId());
        answerEntity.setText(dto.getText());
        answerEntity.setTrue(dto.isTrue());
        answerEntity.setQuestionId(dto.getQuestionId());
        return answerEntity;
    };


    @Override
    public AnswerDto entityToDto(AnswerEntity answerEntity) {
        return entityToDto.apply(answerEntity);
    }

    @Override
    public List<AnswerDto> entityToDto(List<AnswerEntity> entities) {
        if(entities.isEmpty())
            return Collections.emptyList();

        return entities.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public AnswerEntity dtoToEntity(AnswerDto answerDto) {
        return dtoToEntity.apply(answerDto);
    }

    @Override
    public List<AnswerEntity> dtoToEntity(List<AnswerDto> dtos) {
        if(dtos.isEmpty())
            return Collections.emptyList();

        return dtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
