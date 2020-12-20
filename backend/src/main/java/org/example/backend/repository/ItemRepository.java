package org.example.backend.repository;

import org.example.backend.domain.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    Optional<Item> findByName(String name);
}
