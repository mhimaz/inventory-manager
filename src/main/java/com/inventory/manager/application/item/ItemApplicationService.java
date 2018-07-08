package com.inventory.manager.application.item;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.manager.application.item.dto.CreateItemRequestDTO;
import com.inventory.manager.application.item.dto.GetItemResponseDTO;
import com.inventory.manager.application.item.dto.ListItemsResponseDTO;
import com.inventory.manager.application.item.dto.UpdateItemPriceRequestDTO;
import com.inventory.manager.application.item.dto.UpdateItemRequestDTO;
import com.inventory.manager.application.shared.dto.LocationQuantityDTO;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.item.ItemService;
import com.inventory.manager.domain.item.ItemStock;
import com.inventory.manager.domain.item.price.ItemPrice;
import com.inventory.manager.domain.item.price.ItemPriceService;
import com.inventory.manager.domain.location.Location;
import com.inventory.manager.domain.location.LocationService;
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
    private ItemPriceService itemPriceService;

    @Autowired
    private ItemTransformer itemTransformer;

    @Autowired
    private ItemSpecification itemSpecification;

    @Autowired
    private LocationService locationService;

    @Transactional
    public Integer createItem(CreateItemRequestDTO requestDTO) {
        logger.info("[ Creating Item :: " + requestDTO + " ]");

        Supplier supplier = supplierService.findSupplierById(requestDTO.getSupplierId());

        List<Location> locations = null;
        List<Integer> locationIds = null;

        if (!requestDTO.getLocationQuantities().isEmpty()) {
            locationIds = requestDTO.getLocationQuantities().stream().map(LocationQuantityDTO::getLocationId)
                    .collect(Collectors.toList());

            locations = locationService.findByLocationIds(locationIds);
        }

        itemSpecification.isSatisfiedBy(requestDTO, supplier, locations, locationIds);

        Item item = itemTransformer.toItem(supplier, requestDTO);
        List<ItemStock> itemStocks = itemTransformer.toItemStocks(requestDTO.getLocationQuantities(), locations);
        ItemPrice itemPrice = itemTransformer.toItemPrice(requestDTO.getPrice());

        item = itemService.createItem(item, itemStocks, itemPrice);
        return item.getId();
    }

    @Transactional
    public GetItemResponseDTO updateItem(Integer itemId, UpdateItemRequestDTO requestDTO) {
        logger.info("[ Updating Item :: itemId: " + itemId + ", requestDTO: " + requestDTO + " ]");

        Item item = itemService.findItemById(itemId);

        itemSpecification.isSatisfiedBy(requestDTO, item);

        item = itemTransformer.toItem(requestDTO, item);

        item = itemService.updateItem(item);
        return itemTransformer.toGetItemResponseDTO(item);
    }

    @Transactional
    public GetItemResponseDTO updateItemPrice(Integer itemId, UpdateItemPriceRequestDTO requestDTO) {
        logger.info("[ Updating Item Price :: itemId: " + itemId + ", requestDTO: " + requestDTO + " ]");

        Item item = itemService.findItemById(itemId);

        itemSpecification.isSatisfiedBy(requestDTO, item);

        ItemPrice itemPrice = null;
        if (item.getPrice() == null) {
            itemPrice = itemTransformer.toItemPrice(requestDTO.getPrice());
        } else {
            itemPrice = itemTransformer.toItemPrice(requestDTO.getPrice(), item.getPrice());
        }

        // TODO - can pass itemPrice in to updateItem() method
        itemPriceService.updateItemPrice(itemPrice);

        item.setPrice(itemPrice);
        item = itemService.updateItem(item);

        return itemTransformer.toGetItemResponseDTO(item);
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
