package org.example.backend.service;


import org.example.backend.controller.dto.ItemDto;
import org.example.backend.domain.Item;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ItemService {
    Item saveItem(Item item);

    Optional<Item> getItemById(Long id);

    Map<Item, Integer> getFullRecipe(Item item);

    Optional<Item> getItemByName(String name);

    List<Item> getAllItems();

    void deleteItemById(Long id);

    default Item createItemFrom(ItemDto itemDto) {
        throw new UnsupportedOperationException();
    }

}
