package org.example.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.controller.dto.CraftRecipeEntry;
import org.example.backend.controller.dto.ItemDto;
import org.example.backend.domain.Item;
import org.example.backend.exception.ItemNotFoundException;
import org.example.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemPackService itemPackService;

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
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
        return itemRepository.findByName(name);
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> result = new ArrayList<>();
        itemRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public void deleteItemById(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public Item createItemFrom(ItemDto itemDto) {
        try {
            return new Item(
                    itemDto.getId(),
                    itemDto.getName(),
                    itemDto.getIcon() == null ? null : itemDto.getIcon().getBytes(),
                    createCraftRecipeFrom(createRecipeEntriesFromJson(itemDto.getCraftRecipe())),
                    itemPackService.getItemPackById(itemDto.getItemPackId()).orElse(null));
        } catch (IOException e) {
            throw new RuntimeException("Recipe has not been serialized", e);
        }
    }

    private List<CraftRecipeEntry> createRecipeEntriesFromJson(String craftRecipeJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(craftRecipeJson, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Recipe has not been serialized", e);
        }
    }

    private Map<Item, Integer> createCraftRecipeFrom(List<CraftRecipeEntry> craftRecipeEntries) {
        Map<Item, Integer> craftRecipe = new HashMap<>();
        craftRecipeEntries.forEach(recipeEntry -> {
            Item item = this.getItemById(recipeEntry.getId())
                    .orElseThrow(() -> new ItemNotFoundException("Recipe has nonexistent item with id: " + recipeEntry.getId()));
            craftRecipe.put(item, recipeEntry.getAmount());
        });
        return craftRecipe;
    }
}
