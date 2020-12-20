package org.example.backend.controller.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ItemPackDto {
    private Long id;

    private String name;

    MultipartFile picture;

    private List<Long> items;

    private String owner;

    private boolean isPrivate = true;
}
