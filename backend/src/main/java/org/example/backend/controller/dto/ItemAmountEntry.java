package org.example.backend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.backend.domain.Item;

@Data
@AllArgsConstructor
public class ItemAmountEntry {
    private Item item;
    private Integer amount;
}
