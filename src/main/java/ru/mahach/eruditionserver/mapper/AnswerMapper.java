package ru.mahach.eruditionserver.mapper;

import org.mapstruct.Mapper;
import ru.mahach.eruditionserver.dto.AnswerDto;
import ru.mahach.eruditionserver.entity.AnswerEntity;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    AnswerEntity answerDtoToEntity(AnswerDto answerDto);

    AnswerDto answerEntityToDto(AnswerEntity answerEntity);
}
