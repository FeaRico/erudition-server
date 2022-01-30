package ru.mahach.eruditionserver.mapper;

import org.mapstruct.Mapper;
import ru.mahach.eruditionserver.dto.ItemDto;
import ru.mahach.eruditionserver.entity.ItemEntity;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemEntity itemDtoToEntity(ItemDto itemDto);

    ItemDto itemEntityToDto(ItemEntity itemEntity);
}
