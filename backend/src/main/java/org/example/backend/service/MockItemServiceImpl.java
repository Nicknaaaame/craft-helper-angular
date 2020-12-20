package org.example.backend.service;

import org.example.backend.domain.Item;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MockItemServiceImpl implements ItemService {
    private List<Item> items;

    public MockItemServiceImpl() {
        items = MockDataSource.getItems();
        MockDataSource.init(this);
    }

    @Override
    public Item saveItem(Item item) {
        items.add(item);
        return item;
    }

    @Override
    public Optional<Item> getItemById(Long id) {
        return items.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    @Override
    public Map<Item, Integer> getFullRecipe(Item item) {
        Map<Item, Integer> craftRecipe = item.getCraftRecipe();
        if (craftRecipe.isEmpty())
            return new HashMap<>();
        else {
            Map<Item, Integer> fullRecipe = new HashMap<>();
            for (Map.Entry<Item, Integer> entry : craftRecipe.entrySet()) {
                Map<Item, Integer> currFullRecipe = getFullRecipe(entry.getKey());
                Integer itemAmount = entry.getValue();
                if (currFullRecipe.isEmpty()) {
                    fullRecipe.merge(entry.getKey(), itemAmount, (prev, next) -> prev + next * itemAmount);
                } else {
                    currFullRecipe.forEach((currItem, currAmount) ->
                            fullRecipe.merge(currItem, currAmount * itemAmount, (prev, next) -> prev + next * itemAmount));
                }

            }
            return fullRecipe;
        }
    }

    @Override
    public Optional<Item> getItemByName(String name) {
        return items.stream().filter(item -> item.getName().equals(name)).findFirst();
    }

    @Override
    public List<Item> getAllItems() {
        return items;
    }

    @Override
    public void deleteItemById(Long id) {
        throw new UnsupportedOperationException();
    }
}