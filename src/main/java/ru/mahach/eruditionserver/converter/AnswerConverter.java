package ru.mahach.eruditionserver.converter;

import ru.mahach.eruditionserver.models.dto.AnswerDto;
import ru.mahach.eruditionserver.models.entity.AnswerEntity;

import java.util.List;

public interface AnswerConverter {
    AnswerDto entityToDto(AnswerEntity answerEntity);

    List<AnswerDto> entityToDto(List<AnswerEntity> entities);

    AnswerEntity dtoToEntity(AnswerDto answerDto);

    List<AnswerEntity> dtoToEntity(List<AnswerDto> dtos);
}
