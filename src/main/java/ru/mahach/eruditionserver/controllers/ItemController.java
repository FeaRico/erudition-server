package ru.mahach.eruditionserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mahach.eruditionserver.exceptions.ItemNotFoundException;
import ru.mahach.eruditionserver.exceptions.base.ItemException;
import ru.mahach.eruditionserver.models.Item;
import ru.mahach.eruditionserver.services.ItemService;

import java.util.Collection;

@RestController
@RequestMapping(value = "api/v1/item")
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item){
        return ResponseEntity.ok(itemService.createItem(item)
                .orElseThrow(() -> new ItemException("Failed to create item")));
    }

    @PutMapping
    public ResponseEntity<Item> updateItem(@RequestBody Item item){
        return ResponseEntity.ok(itemService.updateItem(item)
                .orElseThrow(() -> new ItemException("Failed to update item")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Item> deleteItem(@PathVariable Long id){
        return ResponseEntity.ok(itemService.deleteItemById(id)
                .orElseThrow(() -> new ItemException("Failed to delete item")));
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Item>> allItems(){
        Collection<Item> result = itemService.itemFindAll();
        if(result.isEmpty()){
            throw new ItemNotFoundException();
        }
        return ResponseEntity.ok(result);
    }


}
