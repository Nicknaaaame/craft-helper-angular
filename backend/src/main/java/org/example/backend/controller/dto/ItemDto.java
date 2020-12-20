package org.example.backend.controller.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ItemDto{
    private Long id;
    private String name;
    private MultipartFile icon;
    private String craftRecipe;
    private Long itemPackId;
}

