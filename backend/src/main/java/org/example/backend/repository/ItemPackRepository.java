package org.example.backend.repository;

import org.example.backend.domain.ItemPack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPackRepository extends JpaRepository<ItemPack, Long> {
}
