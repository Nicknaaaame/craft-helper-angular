package org.example.backend.service;


import org.example.backend.domain.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MockDataSource {
    private static final List<Item> items = new ArrayList<>();


    public static List<Item> getItems() {
        return items;
    }

    public static void init(ItemService itemService) {
        itemService.saveItem(new Item(1L, "Серебрянная руда"));
        itemService.saveItem(new Item(4L, "Кожанные ремни"));
        itemService.saveItem(new Item(5L, "Куски кожи", new HashMap<>() {{
            put(itemService.getItemById(4L).get(), 2);
        }}));
        itemService.saveItem(new Item(2L, "Серебрянный слиток", new HashMap<>() {{
            put(itemService.getItemById(1L).get(), 2);
        }}));
        itemService.saveItem(new Item(3L, "Серебрянный меч", new HashMap<>() {{
            put(itemService.getItemById(2L).get(), 2);
            put(itemService.getItemById(5L).get(), 3);
        }}));
        itemService.saveItem(new Item(6L, "Говно", new HashMap<>() {{
            put(itemService.getItemById(3L).get(), 2);
        }}));
    }
}
