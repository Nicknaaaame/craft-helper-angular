package org.example.backend.service;

import org.example.backend.controller.dto.ItemPackDto;
import org.example.backend.domain.Item;
import org.example.backend.domain.ItemPack;
import org.example.backend.exception.ItemNotFoundException;
import org.example.backend.exception.UserNotFoundException;
import org.example.backend.repository.ItemPackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemPackServiceImpl implements ItemPackService {
    @Autowired
    private ItemPackRepository itemPackRepository;
    @Qualifier("itemServiceImpl")
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    @Override
    public ItemPack saveItemPack(ItemPack itemPack) {
        return itemPackRepository.save(itemPack);
    }

    @Override
    public ItemPack saveItemPackFrom(ItemPackDto itemPackDto) {
        List<Item> items = itemPackDto.getItems().stream().map(id -> itemService.getItemById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with id:" + id))).collect(Collectors.toList());
        try {
            return itemPackRepository.save(new ItemPack(
                    itemPackDto.getId(),
                    itemPackDto.getName(),
                    itemPackDto.getPicture() == null ? null : itemPackDto.getPicture().getBytes(),
                    items,
                    userService.getUserById(itemPackDto.getOwner())
                            .orElseThrow(() -> new UserNotFoundException("User not found with id:" + itemPackDto.getOwner())),
                    itemPackDto.isPrivate()));
        } catch (IOException e) {
            throw new RuntimeException("Picture has not been serialized", e);
        }
    }

    @Override
    public Optional<ItemPack> getItemPackById(Long id) {
        return itemPackRepository.findById(id);
    }

    @Override
    public List<ItemPack> getAllItemPacks() {
        return itemPackRepository.findAll();
    }

    @Override
    public void deleteItemPackById(Long id) {
        itemPackRepository.deleteById(id);
    }
}
