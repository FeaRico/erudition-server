package ru.mahach.eruditionserver.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mahach.eruditionserver.exceptions.ItemNotFoundException;
import ru.mahach.eruditionserver.models.entity.ItemEntity;
import ru.mahach.eruditionserver.repository.ItemRepository;
import ru.mahach.eruditionserver.services.ItemService;

import java.util.Collection;
import java.util.Optional;

/**
 * Реализация сервиса {@link ItemService}
 * @author Makhach Abdulazizov
 * @version 1.0
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    ItemServiceImpl(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    @Override
    public Optional<ItemEntity> createItem(ItemEntity item) {
        return Optional.of(itemRepository.save(item));
    }

    @Override
    public Optional<ItemEntity> updateItem(ItemEntity item) {
        ItemEntity itemToUpdate = itemRepository.findById(item.getId())
                .orElseThrow(() -> new ItemNotFoundException(item.getId()));
        itemToUpdate.setName(item.getName());
        itemToUpdate.setImagePath(item.getImagePath());
        return Optional.of(itemRepository.save(itemToUpdate));
    }

    @Override
    public Optional<ItemEntity> deleteItemById(Long id) {
        ItemEntity itemToDelete = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
        itemRepository.deleteById(id);
        return Optional.of(itemToDelete);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ItemEntity> itemFindById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ItemEntity> itemFindAll() {
        return itemRepository.findAll();
    }
}
