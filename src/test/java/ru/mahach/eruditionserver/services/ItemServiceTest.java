package ru.mahach.eruditionserver.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mahach.eruditionserver.exceptions.base.ItemException;
import ru.mahach.eruditionserver.models.dto.ItemDto;
import ru.mahach.eruditionserver.models.entity.ItemEntity;
import ru.mahach.eruditionserver.repository.ItemRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test") //TODO: убрать в конфиги
@Transactional
class ItemServiceTest {
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    private final Supplier<ItemDto> createItemDtoWithoutId = () -> new ItemDto(null, "test",
            "test/path");

    @Autowired
    ItemServiceTest(ItemRepository itemRepository, ItemService itemService) {
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    void create() {
        ItemDto itemDto = createItemDtoWithoutId.get();

        Optional<ItemDto> itemDtoOptional = itemService.create(itemDto);
        ItemDto savedItem = itemDtoOptional
                .orElseThrow(() -> new ItemException("Can't create item"));
        assertNotNull(savedItem.getId());

        List<ItemEntity> foundItems = itemRepository.findAll();
        assertEquals(1, foundItems.size());

        equalsItems(savedItem, foundItems.get(0));
    }

    @Test
    void update() {
        ItemDto itemDto = createItemDtoWithoutId.get();

        Optional<ItemDto> itemDtoOptional = itemService.create(itemDto);
        ItemDto savedDto = itemDtoOptional.orElseThrow(() -> new ItemException("Can't create item"));
        assertNotNull(savedDto.getId());

        ItemDto toUpdateItemDto = new ItemDto(savedDto.getId(), "updateTest",
                "new/path");
        Optional<ItemDto> itemDtoOptionalUpdate = itemService.update(toUpdateItemDto);

        ItemDto updatedItem = itemDtoOptionalUpdate
                .orElseThrow(() -> new ItemException("Can't update item with id = " + savedDto.getId()));

        ItemEntity foundItem = itemRepository.getById(updatedItem.getId());

        equalsItems(updatedItem, foundItem);
    }

    @Test
    void deleteById() {
        ItemDto itemDto = createItemDtoWithoutId.get();

        Optional<ItemDto> itemDtoOptional = itemService.create(itemDto);
        ItemDto savedItem = itemDtoOptional
                .orElseThrow(() -> new ItemException("Can't create item"));
        assertNotNull(savedItem.getId());

        List<ItemEntity> foundItemsBefore = itemRepository.findAll();
        assertEquals(1, foundItemsBefore.size());

        Optional<ItemDto> deletedItemOptional = itemService.deleteById(savedItem.getId());
        ItemDto deletedItem = deletedItemOptional
                .orElseThrow(() -> new ItemException("Can't delete item with id = " + savedItem.getId()));

        equalsItems(savedItem, deletedItem);

        List<ItemEntity> foundItemsAfter = itemRepository.findAll();
        assertEquals(0, foundItemsAfter.size());
    }

    @Test
    void getById() {
        ItemDto itemDto = createItemDtoWithoutId.get();

        Optional<ItemDto> itemDtoOptional = itemService.create(itemDto);
        ItemDto savedItem = itemDtoOptional
                .orElseThrow(() -> new ItemException("Can't create item"));
        assertNotNull(savedItem.getId());

        Optional<ItemDto> foundItemOptional = itemService.getById(savedItem.getId());
        ItemDto foundItem = foundItemOptional
                .orElseThrow(() -> new ItemException("Can't find item with id = " + savedItem.getId()));

        equalsItems(savedItem, foundItem);
    }

    @Test
    void getAll() {
        List<ItemDto> itemDtos = IntStream.range(0, 3)
                .mapToObj(i -> createItemDtoWithoutId.get())
                .collect(Collectors.toList());

        List<ItemDto> savedDtos = itemDtos.stream()
                .map(itemService::create)
                .map(itemDto -> itemDto
                        .orElseThrow(() -> new ItemException("Can't create item")))
                .collect(Collectors.toList());
        savedDtos.forEach(item -> assertNotNull(item.getId()));

        List<ItemDto> foundItems = itemService.getAll();
        assertEquals(3, foundItems.size());

        IntStream.range(0, 3).forEach(i -> equalsItems(savedDtos.get(i), foundItems.get(i)));
    }

    private void equalsItems(ItemDto expected, ItemDto actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getImagePath(), actual.getImagePath());
    }

    private void equalsItems(ItemDto expected, ItemEntity actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getImagePath(), actual.getImagePath());
    }
}