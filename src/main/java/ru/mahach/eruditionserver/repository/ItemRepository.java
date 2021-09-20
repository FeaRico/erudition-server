package ru.mahach.eruditionserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mahach.eruditionserver.models.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
