package org.example.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private byte[] picture;

    @OneToMany
    @EqualsAndHashCode.Exclude
    private List<Item> items;

    @OneToOne
    private User owner;

    private boolean isPrivate = true;
}
