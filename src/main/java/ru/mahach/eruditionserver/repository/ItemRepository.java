package ru.mahach.eruditionserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mahach.eruditionserver.models.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
