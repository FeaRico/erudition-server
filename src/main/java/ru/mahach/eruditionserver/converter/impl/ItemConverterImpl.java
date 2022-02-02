package ru.mahach.eruditionserver.converter.impl;

import org.springframework.stereotype.Component;
import ru.mahach.eruditionserver.converter.ItemConverter;
import ru.mahach.eruditionserver.converter.QuestionConverter;
import ru.mahach.eruditionserver.dto.ItemDto;
import ru.mahach.eruditionserver.entity.ItemEntity;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ItemConverterImpl implements ItemConverter {
    private QuestionConverter questionConverter;

    public ItemConverterImpl(QuestionConverter questionConverter) {
        this.questionConverter = questionConverter;
    }

    Function<ItemEntity, ItemDto> entityToDto = entity -> new ItemDto(
      entity.getId(), entity.getName(), entity.getImagePath(),
            questionConverter.entityToDto(entity.getQuestions())
    );

    Function<ItemDto, ItemEntity> dtoToEntity = dto -> {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(dto.getId());
        itemEntity.setName(dto.getName());
        itemEntity.setImagePath(dto.getImagePath());
        itemEntity.setQuestions(questionConverter.dtoToEntity(dto.getQuestions()));

        return itemEntity;
    };

    @Override
    public ItemDto entityToDto(ItemEntity itemEntity) {
        return entityToDto.apply(itemEntity);
    }

    @Override
    public List<ItemDto> entityToDto(List<ItemEntity> entities) {
        return entities.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public ItemEntity dtoToEntity(ItemDto itemDto) {
        return dtoToEntity.apply(itemDto);
    }

    @Override
    public List<ItemEntity> dtoToEntity(List<ItemDto> dtos) {
        return dtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
