package ru.mahach.eruditionserver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.mahach.eruditionserver.models.Item;
import ru.mahach.eruditionserver.services.ItemService;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ItemService itemService;

    @Test
    void shouldCreateItem() throws Exception {
        Item createItem = createItem(null, "Математика", "photo\\items\\math.img");
        MvcResult mvcResult = mvc.perform(post("/api/v1/item")
                .content(asJsonString(createItem))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name", is(createItem.getName())))
                .andExpect(jsonPath("imagePath", is(createItem.getImagePath())))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Integer createdItemId = JsonPath.read(content, "id");
        itemService.deleteItemById((long)createdItemId);
    }

    @Test
    void shouldUpdateItem() throws Exception{
        Optional<Item> itemOptional = itemService.createItem(createItem(null, "Test", "test_path"));
        Item newItem = itemOptional.orElseThrow(IllegalArgumentException::new);
        Item updateItem = createItem(newItem.getId(), "TestUpdate", "testPath");

        mvc.perform(put("/api/v1/item")
                .content(asJsonString(updateItem))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(updateItem.getId().intValue())))
                .andExpect(jsonPath("name", is(updateItem.getName())))
                .andExpect(jsonPath("imagePath", is(updateItem.getImagePath())));

        itemService.deleteItemById(updateItem.getId()).orElseThrow();
    }

    @Test
    void shouldDeleteItem() throws Exception{
        Item createItem = createItem(null, "Test", "textpath");
        Optional<Item> itemOptional = itemService.createItem(createItem);
        Item newItem = itemOptional.orElseThrow(IllegalArgumentException::new);
        int id = newItem.getId().intValue();
        mvc.perform(delete("/api/v1/item/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(id)));
    }

    @Test
    void shouldGetItemById() throws Exception{
        Item createItem = createItem(null, "TestName", "TestPath");
        Optional<Item> itemOptional = itemService.createItem(createItem);
        Item newItem = itemOptional.orElseThrow(IllegalArgumentException::new);
        int id = newItem.getId().intValue();
        mvc.perform(get("/api/v1/item/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(id)))
                .andExpect(jsonPath("name", is(createItem.getName())))
                .andExpect(jsonPath("imagePath", is(createItem.getImagePath())));
        itemService.deleteItemById(newItem.getId());

    }

    private Item createItem(Long id, String name, String imagePath){
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setImagePath(imagePath);
        return item;
    }

    static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}