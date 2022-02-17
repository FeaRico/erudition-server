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
    Optional<ItemDto> create(ItemDto item);

    Optional<ItemDto> update(ItemDto item);

    Optional<ItemDto> deleteById(Long id);

    Optional<ItemDto> getById(Long id);

    List<ItemDto> getAll();
}
