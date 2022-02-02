package ru.mahach.eruditionserver.converter;

import ru.mahach.eruditionserver.dto.QuestionDto;
import ru.mahach.eruditionserver.entity.QuestionEntity;

import java.util.List;

public interface QuestionConverter {
    QuestionDto entityToDto(QuestionEntity questionEntity);

    List<QuestionDto> entityToDto(List<QuestionEntity> entities);

    QuestionEntity dtoToEntity(QuestionDto questionDto);

    List<QuestionEntity> dtoToEntity(List<QuestionDto> dtos);
}
