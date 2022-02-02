package ru.mahach.eruditionserver.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mahach.eruditionserver.converter.ItemConverter;
import ru.mahach.eruditionserver.exceptions.ItemNotFoundException;
import ru.mahach.eruditionserver.models.dto.ItemDto;
import ru.mahach.eruditionserver.models.entity.ItemEntity;
import ru.mahach.eruditionserver.repository.ItemRepository;
import ru.mahach.eruditionserver.services.ItemService;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса {@link ItemService}
 *
 * @author Makhach Abdulazizov
 * @version 1.0
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;

    ItemServiceImpl(ItemRepository itemRepository, ItemConverter itemConverter) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }

    @Override
    public Optional<ItemDto> createItem(ItemDto item) {
        ItemEntity saveEntity = itemRepository.save(itemConverter.dtoToEntity(item));

        return Optional.of(itemConverter.entityToDto(saveEntity));
    }

    @Override
    public Optional<ItemDto> updateItem(ItemDto item) {
        ItemEntity itemToUpdate = itemRepository.findById(item.getId())
                .orElseThrow(() -> new ItemNotFoundException(item.getId()));

        itemToUpdate.setName(item.getName());
        itemToUpdate.setImagePath(item.getImagePath());

        return Optional.of(itemConverter.entityToDto(itemRepository.save(itemToUpdate)));
    }

    @Override
    public Optional<ItemDto> deleteItemById(Long id) {
        ItemEntity itemToDelete = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
        itemRepository.deleteById(id);

        return Optional.of(itemConverter.entityToDto(itemToDelete));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ItemDto> itemFindById(Long id) {
        ItemEntity findEntity = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));

        return Optional.of(itemConverter.entityToDto(findEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> itemFindAll() {
        List<ItemEntity> findItems = itemRepository.findAll();

        return itemConverter.entityToDto(findItems);
    }
}
