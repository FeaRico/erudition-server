package ru.mahach.eruditionserver.services;

import ru.mahach.eruditionserver.models.dto.ItemDto;
import ru.mahach.eruditionserver.models.entity.ItemEntity;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс сервиса {@link ItemEntity}
 *
 * @author Makhach Abdulazizov
 * @version 1.0
 */
public interface ItemService {
    Optional<ItemDto> createItem(ItemDto item);

    Optional<ItemDto> updateItem(ItemDto item);

    Optional<ItemDto> deleteItemById(Long id);

    Optional<ItemDto> getItemById(Long id);

    List<ItemDto> getAllItems();
}
