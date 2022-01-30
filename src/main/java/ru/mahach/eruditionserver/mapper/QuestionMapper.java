package ru.mahach.eruditionserver.mapper;

import org.mapstruct.Mapper;
import ru.mahach.eruditionserver.dto.QuestionDto;
import ru.mahach.eruditionserver.entity.QuestionEntity;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionEntity questionDtoToEntity(QuestionDto questionDto);

    QuestionDto questionEntityToDto(QuestionEntity questionEntity);
}
