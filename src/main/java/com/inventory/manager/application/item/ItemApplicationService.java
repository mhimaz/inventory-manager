package com.inventory.manager.application.item;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.manager.application.item.dto.CreateItemRequestDTO;
import com.inventory.manager.application.item.dto.ListItemsResponseDTO;
import com.inventory.manager.application.item.dto.UpdateItemRequestDTO;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.item.ItemService;
import com.inventory.manager.domain.item.ItemStock;
import com.inventory.manager.domain.supplier.Supplier;
import com.inventory.manager.domain.supplier.SupplierService;
import com.inventory.manager.exception.CustomExceptionCodes;
import com.inventory.manager.exception.NotFoundException;

@Service
public class ItemApplicationService {

    private static final Logger logger = Logger.getLogger(ItemApplicationService.class);

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemTransformer itemTransformer;

    @Autowired
    private ItemSpecification itemSpecification;

    @Transactional
    public Integer createItem(CreateItemRequestDTO requestDTO) {
        logger.info("[ Creating Item :: " + requestDTO + " ]");

        Supplier supplier = supplierService.findSupplierById(requestDTO.getSupplierId());

        itemSpecification.isSatisfiedBy(requestDTO, supplier);

        Item item = itemTransformer.toItem(supplier, requestDTO);
        List<ItemStock> itemStocks = itemTransformer.toItemStocks(requestDTO.getLocationQuantities());

        item = itemService.createItem(item, itemStocks);
        return item.getId();
    }

    @Transactional
    public void updateItem(Integer itemId, UpdateItemRequestDTO requestDTO) {
        logger.info("[ Updating Item :: itemId: " + itemId + ", requestDTO: " + requestDTO + " ]");

        Item item = itemService.findItemById(itemId);

        itemSpecification.isSatisfiedBy(requestDTO, item);

        item = itemTransformer.toItem(requestDTO, item);

        itemService.updateItem(item);
    }

    @Transactional
    public ListItemsResponseDTO listItems(String keyword, int page, int count, String sortDirection, String sortBy) {

        logger.info("[ List Items ]");

        Page<Item> items = itemService.findAll(keyword, page, count, sortDirection, sortBy);

        return itemTransformer.toListItemsResponseDTO(items);
    }

    @Transactional
    public ListItemsResponseDTO listItemsBySupplier(Integer supplierId, String keyword, int page, int count,
            String sortDirection, String sortBy) {

        logger.info("[ List Items By Supplier ]");

        Page<Item> items = itemService.findAllBySupplier(supplierId, keyword, page, count, sortDirection, sortBy);

        return itemTransformer.toListItemsResponseDTO(items);
    }

    @Transactional
    public ListItemsResponseDTO getItemsBySupplier(Integer supplierId) {

        logger.info("[ Get Items By Supplier ]");

        Supplier supplier = supplierService.findSupplierById(supplierId);

        List<Item> items = itemService.findBySupplier(supplier);

        return itemTransformer.toListItemsResponseDTO(items);
    }

    @Transactional
    public void deleteItem(Integer itemId) {
        logger.info("[ Deleting Item :: itemId: " + itemId + " ]");

        Item item = itemService.findItemById(itemId);

        if (item == null) {
            throw new NotFoundException(CustomExceptionCodes.ITEM_NOT_FOUND.name(), "Item not found");
        }

        itemService.deleteItem(item);
    }
}
