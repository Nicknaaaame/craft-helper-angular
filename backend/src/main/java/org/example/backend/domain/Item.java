package org.example.backend.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Lob
    private byte[] icon;

    @ElementCollection
    @Column(name = "item_amount")
    @JsonSerialize(using = CraftRecipeSerializer.class)
    private Map<Item, Integer> craftRecipe = new HashMap<>();

    @ManyToOne
    @ToString.Exclude
    private ItemPack itemPack;

    public Item(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Item(Long id, String name, Map<Item, Integer> craftRecipe) {
        this(id, name);
        this.craftRecipe = craftRecipe;
    }
}
