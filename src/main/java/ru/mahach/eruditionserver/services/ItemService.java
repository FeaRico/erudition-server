package ru.mahach.eruditionserver.services;

import java.util.Collection;
import java.util.Optional;

import ru.mahach.eruditionserver.models.entity.ItemEntity;

/**
 * Интерфейс сервиса {@link ItemEntity}
 * @author Makhach Abdulazizov
 * @version 1.0
 */
public interface ItemService {

    Optional<ItemEntity> createItem(ItemEntity item);

    Optional<ItemEntity> updateItem(ItemEntity item);

    Optional<ItemEntity> deleteItemById(Long id);

    Optional<ItemEntity> itemFindById(Long id);

    Collection<ItemEntity> itemFindAll();

}
