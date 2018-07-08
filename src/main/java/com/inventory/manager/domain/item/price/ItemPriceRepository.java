package com.inventory.manager.domain.item.price;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPriceRepository extends CrudRepository<ItemPrice, Integer> {

}
