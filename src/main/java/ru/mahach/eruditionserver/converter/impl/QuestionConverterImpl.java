package ru.mahach.eruditionserver.converter.impl;

import org.springframework.stereotype.Component;
import ru.mahach.eruditionserver.converter.AnswerConverter;
import ru.mahach.eruditionserver.converter.QuestionConverter;
import ru.mahach.eruditionserver.models.dto.QuestionDto;
import ru.mahach.eruditionserver.models.entity.QuestionEntity;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class QuestionConverterImpl implements QuestionConverter {
    private AnswerConverter answerConverter;

    public QuestionConverterImpl(AnswerConverter answerConverter) {
        this.answerConverter = answerConverter;
    }

    Function<QuestionEntity, QuestionDto> entityToDto = entity -> new QuestionDto.Builder()
            .setId(entity.getId())
            .setText(entity.getText())
            .setItemId(entity.getItemId())
            .setImagePath(entity.getImagePath())
            .setAnswers(answerConverter.entityToDto(entity.getAnswers()))
            .build();

    Function<QuestionDto, QuestionEntity> dtoToEntity = dto -> {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(dto.getId());
        questionEntity.setText(dto.getText());
        questionEntity.setItemId(dto.getItemId());
        questionEntity.setImagePath(dto.getImagePath());
        questionEntity.setAnswers(answerConverter.dtoToEntity(dto.getAnswers()));

        return questionEntity;
    };

    @Override
    public QuestionDto entityToDto(QuestionEntity questionEntity) {
        return entityToDto.apply(questionEntity);
    }

    @Override
    public List<QuestionDto> entityToDto(List<QuestionEntity> entities) {
        return entities.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public QuestionEntity dtoToEntity(QuestionDto questionDto) {
        return dtoToEntity.apply(questionDto);
    }

    @Override
    public List<QuestionEntity> dtoToEntity(List<QuestionDto> dtos) {
        return dtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
