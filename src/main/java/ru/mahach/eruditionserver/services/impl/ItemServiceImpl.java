package ru.mahach.eruditionserver.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mahach.eruditionserver.exceptions.ItemNotFoundException;
import ru.mahach.eruditionserver.models.Item;
import ru.mahach.eruditionserver.repository.ItemRepository;
import ru.mahach.eruditionserver.services.ItemService;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    ItemServiceImpl(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    @Override
    public Optional<Item> createItem(Item item) {
        return Optional.of(itemRepository.save(item));
    }

    @Override
    public Optional<Item> updateItem(Item item) {
        Item itemToUpdate = itemRepository.findById(item.getId())
                .orElseThrow(() -> new ItemNotFoundException(item.getId()));
        return Optional.of(itemRepository.save(itemToUpdate));
    }

    @Override
    public Optional<Item> deleteItemById(Long id) {
        Item itemToDelete = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
        itemRepository.deleteById(id);
        return Optional.of(itemToDelete);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Item> itemFindById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Item> itemFindAll() {
        return itemRepository.findAll();
    }
}
