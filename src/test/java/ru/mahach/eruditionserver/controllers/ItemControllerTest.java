package ru.mahach.eruditionserver.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.mahach.eruditionserver.exceptions.base.ItemException;
import ru.mahach.eruditionserver.models.dto.ItemDto;
import ru.mahach.eruditionserver.repository.ItemRepository;
import ru.mahach.eruditionserver.services.ItemService;
import ru.mahach.eruditionserver.utils.Utility;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ItemControllerTest {
    private final MockMvc mvc;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @Autowired
    public ItemControllerTest(MockMvc mvc, ItemRepository itemRepository, ItemService itemService) {
        this.mvc = mvc;
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    void itShouldCreateItem() throws Exception {
        ItemDto createItem = Utility.createItemDto();

        mvc.perform(post("/api/v1/items")
                .content(Utility.objectToJsonString(createItem))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name", is(createItem.getName())))
                .andExpect(jsonPath("imagePath", is(createItem.getImagePath())))
                .andReturn();
    }

    @Test
    void itShouldUpdateItem() throws Exception {
        ItemDto createItem = Utility.createItemDto();

        Optional<ItemDto> itemOptional = itemService.createItem(createItem);
        ItemDto savedItem = itemOptional
                .orElseThrow(() -> new ItemException("Can't create item"));

        ItemDto updateItem = new ItemDto(savedItem.getId(), "newName", "new/path");

        mvc.perform(put("/api/v1/items")
                .content(Utility.objectToJsonString(updateItem))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(updateItem.getId().intValue())))
                .andExpect(jsonPath("name", is(updateItem.getName())))
                .andExpect(jsonPath("imagePath", is(updateItem.getImagePath())));
    }

    @Test
    void itShouldDeleteItem() throws Exception {
        ItemDto createItem = Utility.createItemDto();

        Optional<ItemDto> itemOptional = itemService.createItem(createItem);
        ItemDto savedItem = itemOptional
                .orElseThrow(() -> new ItemException("Can't create item"));

        int id = savedItem.getId().intValue();
        mvc.perform(delete("/api/v1/items/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(id)));
    }

    @Test
    void itShouldGetItemById() throws Exception {
        ItemDto createItem = Utility.createItemDto();

        Optional<ItemDto> itemOptional = itemService.createItem(createItem);
        ItemDto savedItem = itemOptional
                .orElseThrow(() -> new ItemException("Can't create item"));

        int id = savedItem.getId().intValue();
        mvc.perform(get("/api/v1/items/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(id)))
                .andExpect(jsonPath("name", is(createItem.getName())))
                .andExpect(jsonPath("imagePath", is(createItem.getImagePath())));
    }
}
