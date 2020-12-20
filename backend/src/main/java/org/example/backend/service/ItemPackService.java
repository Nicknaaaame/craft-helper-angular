package org.example.backend.service;


import org.example.backend.controller.dto.ItemPackDto;
import org.example.backend.domain.ItemPack;

import java.util.List;
import java.util.Optional;

public interface ItemPackService {
    ItemPack saveItemPack(ItemPack itemPack);

    ItemPack saveItemPackFrom(ItemPackDto itemPack);

    Optional<ItemPack> getItemPackById(Long id);

    List<ItemPack> getAllItemPacks();

    void deleteItemPackById(Long id);
}
