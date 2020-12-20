package org.example.backend.controller;

import org.example.backend.controller.dto.ItemPackDto;
import org.example.backend.domain.ItemPack;
import org.example.backend.exception.ItemPackNotFoundException;
import org.example.backend.service.ItemPackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pack")
public class ItemPackController {
    @Autowired
    private ItemPackService itemPackService;

    @GetMapping("/{id}")
    public ResponseEntity<ItemPack> getItemPackById(@PathVariable Long id) {
        return new ResponseEntity<>(itemPackService.getItemPackById(id).orElseThrow(() ->
                new ItemPackNotFoundException("pack with id: " + id + "was not found")),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ItemPack>> getAllItemPacks() {
        return new ResponseEntity<>(itemPackService.getAllItemPacks(), HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ItemPack> postItemPack(@RequestBody ItemPackDto itemPackDto) {
        return new ResponseEntity<>(itemPackService.saveItemPackFrom(itemPackDto), HttpStatus.OK);
    }

    @ExceptionHandler({ItemPackNotFoundException.class})
    public ResponseEntity<ErrorResponse> ItemPackNotFoundHandler(ItemPackNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), ""), HttpStatus.BAD_REQUEST);
    }
}
