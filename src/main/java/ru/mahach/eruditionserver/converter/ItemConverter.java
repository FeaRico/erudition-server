package ru.mahach.eruditionserver.converter;

import ru.mahach.eruditionserver.models.dto.ItemDto;
import ru.mahach.eruditionserver.models.entity.ItemEntity;

import java.util.List;

public interface ItemConverter {
    ItemDto entityToDto(ItemEntity itemEntity);

    List<ItemDto> entityToDto(List<ItemEntity> entities);

    ItemEntity dtoToEntity(ItemDto itemDto);

    List<ItemEntity> dtoToEntity(List<ItemDto> dtos);
}
