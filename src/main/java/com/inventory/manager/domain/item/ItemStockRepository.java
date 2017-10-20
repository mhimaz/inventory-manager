package com.inventory.manager.domain.item;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.inventory.manager.domain.enums.Location;

@Repository
public interface ItemStockRepository extends CrudRepository<ItemStock, Integer> {

    List<ItemStock> findByItemAndIsDeleted(Item item, Boolean isDeleted);

    ItemStock findByItemAndLocationAndIsDeleted(Item item, Location location, Boolean isDeleted);
}
