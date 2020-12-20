package org.example.backend.controller;

import org.example.backend.controller.dto.ItemDto;
import org.example.backend.domain.Item;
import org.example.backend.exception.ItemNotFoundException;
import org.example.backend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    @Autowired
    @Qualifier("itemServiceImpl")
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id).map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .orElseThrow(() -> new ItemNotFoundException("Item with id: " + id + " not found"));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Item> postItem(@ModelAttribute ItemDto itemDto) {
        Item item = itemService.saveItem(itemService.createItemFrom(itemDto));
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    //TODO: change this to true REST
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Item> updateItem(@ModelAttribute ItemDto itemDto) {
        Item item = itemService.saveItem(itemService.createItemFrom(itemDto));
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteItemById(@PathVariable Long id) {
        itemService.deleteItemById(id);
    }

    @ExceptionHandler({ItemNotFoundException.class})
    public ResponseEntity<ErrorResponse> itemNotFoundHandler(ItemNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), ""), HttpStatus.BAD_REQUEST);
    }
}
