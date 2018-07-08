package com.inventory.manager.domain.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.manager.domain.location.Location;

//import com.inventory.manager.domain.enums.Location;

@Service
public class ItemStockService {

    @Autowired
    private ItemStockRepository itemStockRepo;

    public ItemStock createItemStock(ItemStock itemStock) {

        itemStock.setIsDeleted(false);
        return itemStockRepo.save(itemStock);
    }

    public void deleteItemStock(ItemStock itemStock) {

        itemStock.setIsDeleted(true);
        itemStockRepo.save(itemStock);
    }

    public void deleteItemStocks(List<ItemStock> itemStocks) {

        itemStocks.forEach(is -> is.setIsDeleted(true));
        itemStockRepo.save(itemStocks);
    }

    public void updateItemStock(ItemStock itemStock) {

        itemStockRepo.save(itemStock);
    }

    public List<ItemStock> findItemStocks(Item item) {

        return itemStockRepo.findByItemAndIsDeleted(item, false);
    }

    public ItemStock findItemStock(Item item, Location location) {

        return itemStockRepo.findByItemAndLocationAndIsDeleted(item, location, false);
    }

    public List<ItemStock> findItemStock(Location location) {

        return itemStockRepo.findByLocationAndIsDeleted(location, false);
    }

}
