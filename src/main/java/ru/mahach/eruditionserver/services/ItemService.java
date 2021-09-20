package ru.mahach.eruditionserver.services;

import java.util.Collection;
import java.util.Optional;

import ru.mahach.eruditionserver.models.Item;

public interface ItemService {

    Optional<Item> createItem(Item item);

    Optional<Item> updateItem(Item item);

    Optional<Item> deleteItemById(Long id);

    Optional<Item> itemFindById(Long id);

    Collection<Item> itemFindAll();

}
