package com.inventory.manager.domain.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.inventory.manager.domain.supplier.Supplier;
import com.inventory.manager.exception.ConflictException;
import com.inventory.manager.exception.CustomExceptionCodes;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private ItemStockRepository itemStockRepo;

    @Autowired
    private ItemStockService itemStockService;

    public Item createItem(Item item) {

        item.setIsDeleted(false);
        return itemRepo.save(item);
    }

    public Item createItem(Item item, List<ItemStock> itemStocks) {

        item.setIsDeleted(false);
        itemRepo.save(item);

        if (itemStocks != null & !itemStocks.isEmpty()) {

            itemStocks.forEach(is -> {
                is.setItem(item);
                is.setIsDeleted(false);
            });
            itemStockRepo.save(itemStocks);
        }
        return item;
    }

    public void deleteItem(Item item) {

        List<ItemStock> itemStocks = itemStockService.findItemStocks(item);

        if (itemStocks.stream().anyMatch(is -> is.getQuantity() > 0)) {
            throw new ConflictException(CustomExceptionCodes.ITEM_CANNOT_BE_DELETED.name(), "Item " + item.getName()
                    + " cannot be deleted, since it has stocks available");
        }

        if (!itemStocks.isEmpty()) {
            for (ItemStock itemStock : itemStocks) {
                itemStockService.deleteItemStock(itemStock);
            }
        }

        item.setIsDeleted(true);
        itemRepo.save(item);
    }

    public void updateItem(Item item) {

        itemRepo.save(item);
    }

    public List<Item> findBySupplier(Supplier supplier) {

        return itemRepo.findBySupplierAndIsDeleted(supplier, false);
    }

    public List<Item> findBySupplierAndItemIds(Supplier supplier, List<Integer> itemIds) {

        return itemRepo.findBySupplierIdAndItemIdsAndIsDeleted(supplier.getId(), itemIds, false);
    }

    public List<Item> findByItemIds(List<Integer> itemIds) {

        return itemRepo.findByItemIdsAndIsDeleted(itemIds, false);
    }

    public Page<Item> findAll(String keyword, Integer page, Integer count, String sortDirection, String sortBy) {

        Pageable pageable = new PageRequest(page, count, Direction.fromString(sortDirection), sortBy);
        Page<Item> items = itemRepo.findAll(keyword, pageable);
        items.forEach(i -> i.getItemStocks().removeIf(is -> is.getIsDeleted()));
        return items;
    }

    public Page<Item> findAllBySupplier(Integer supplierId, String keyword, Integer page, Integer count,
            String sortDirection, String sortBy) {

        Pageable pageable = new PageRequest(page, count, Direction.fromString(sortDirection), sortBy);
        Page<Item> items = itemRepo.findAllBySupplier(supplierId, keyword, pageable);
        items.forEach(i -> i.getItemStocks().removeIf(is -> is.getIsDeleted()));
        return items;
    }

    public Boolean isItemCodeExist(String code) {

        Item item = itemRepo.findByCodeAndIsDeleted(code, false);
        return item != null;
    }

    public Boolean isItemNameExist(String name) {

        Item item = itemRepo.findByNameAndIsDeleted(name, false);
        return item != null;
    }

    public Item findItemById(Integer id) {

        return itemRepo.findByIdAndIsDeleted(id, false);
    }
}
