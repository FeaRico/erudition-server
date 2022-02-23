package ru.mahach.eruditionserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mahach.eruditionserver.exceptions.ItemNotFoundException;
import ru.mahach.eruditionserver.exceptions.base.ItemException;
import ru.mahach.eruditionserver.models.dto.ItemDto;
import ru.mahach.eruditionserver.services.ItemService;
import ru.mahach.eruditionserver.validation.Marker;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/items")
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
@Validated
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    @Validated(Marker.Create.class)
    public ResponseEntity<ItemDto> createItem(@RequestBody @Valid ItemDto item) {
        return ResponseEntity.ok(itemService.createItem(item)
                .orElseThrow(() -> new ItemException("Failed to create item")));
    }

    @PutMapping
    @Validated(Marker.Update.class)
    public ResponseEntity<ItemDto> updateItem(@RequestBody @Valid ItemDto item) {
        return ResponseEntity.ok(itemService.updateItem(item)
                .orElseThrow(() -> new ItemException("Failed to update item with id = " + item.getId())));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ItemDto> deleteItem(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(itemService.deleteItemById(id)
                .orElseThrow(() -> new ItemException("Failed to delete item with id = " + id)));
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> allItems() {
        List<ItemDto> result = itemService.getAllItems();
        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<ItemDto> itemById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(itemService.getItemById(id)
                .orElseThrow(() -> new ItemNotFoundException(id)));
    }
}
