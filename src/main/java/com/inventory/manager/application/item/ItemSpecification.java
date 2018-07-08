package com.inventory.manager.application.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inventory.manager.application.item.dto.CreateItemRequestDTO;
import com.inventory.manager.application.item.dto.UpdateItemPriceRequestDTO;
import com.inventory.manager.application.item.dto.UpdateItemRequestDTO;
import com.inventory.manager.application.shared.dto.LocationQuantityDTO;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.item.ItemService;
import com.inventory.manager.domain.location.Location;
import com.inventory.manager.domain.supplier.Supplier;
import com.inventory.manager.exception.BadRequestException;
import com.inventory.manager.exception.CustomExceptionCodes;
import com.inventory.manager.exception.DuplicateException;
import com.inventory.manager.exception.NotFoundException;

@Component
public class ItemSpecification {

    @Autowired
    private ItemService itemService;

    public boolean isSatisfiedBy(CreateItemRequestDTO requestDTO, Supplier supplier, List<Location> locations,
            List<Integer> locationIds) {

        if (supplier == null) {
            throw new NotFoundException(CustomExceptionCodes.SUPPLIER_NOT_FOUND.name(),
                    "Supplier not found for the id: " + requestDTO.getSupplierId());
        }

        if (!requestDTO.getLocationQuantities().isEmpty()) {
            // check if a location exists more than once
            requestDTO.getLocationQuantities().forEach(
                    lq -> {
                        if (requestDTO.getLocationQuantities().stream().map(LocationQuantityDTO::getLocationId)
                                .filter(lq.getLocationId()::equals).count() > 1) {
                            throw new DuplicateException(CustomExceptionCodes.DUPLICATE_LOCATION.name(),
                                    "Duplicate location found: " + lq.getLocationId());

                        } else if (lq.getQuantity() <= 0) {
                            throw new BadRequestException(CustomExceptionCodes.INVALID_QUANTITY.name(),
                                    "Invalid quantity: " + lq.getQuantity());
                        }
                    });

            if (locations.size() != locationIds.size()) {
                throw new NotFoundException("Location not found for one of the location ids: " + locationIds);
            }
        }

        if (requestDTO.getCode() != null && itemService.isItemCodeExist(requestDTO.getCode())) {
            throw new DuplicateException(CustomExceptionCodes.DUPLICATE_ITEM_CODE.name(), "Duplicate item code: "
                    + requestDTO.getCode());
        }

        if (itemService.isItemNameExist(requestDTO.getName())) {
            throw new DuplicateException(CustomExceptionCodes.DUPLICATE_ITEM_NAME.name(), "Duplicate item name: "
                    + requestDTO.getName());
        }

        if (requestDTO.getPrice() == null) {
            throw new BadRequestException(CustomExceptionCodes.PRICE_REQUIRED.name(), "Item price is required");
        }

        if (requestDTO.getPrice().getBuyingPrice() <= 0 || requestDTO.getPrice().getSellingPrice() <= 0) {
            throw new BadRequestException(CustomExceptionCodes.INVALID_PRICE.name(), "Invalid buying or selling price");
        }

        return true;
    }

    public boolean isSatisfiedBy(UpdateItemRequestDTO requestDTO, Item item) {

        if (item == null) {
            throw new NotFoundException(CustomExceptionCodes.ITEM_NOT_FOUND.name(), "Item not found");
        }

        if (requestDTO.getName() == null) {
            throw new BadRequestException(CustomExceptionCodes.ITEM_NAME_REQUIRED.name(), "Item name is required");
        }

        if (requestDTO.getCode() == null) {
            throw new BadRequestException(CustomExceptionCodes.ITEM_CODE_REQUIRED.name(), "Item code is required");
        }

        if (!item.getName().equals(requestDTO.getName()) && itemService.isItemNameExist(requestDTO.getName())) {
            throw new DuplicateException(CustomExceptionCodes.DUPLICATE_ITEM_NAME.name(), "Duplicate item name: "
                    + requestDTO.getName());
        }

        if (!item.getCode().equals(requestDTO.getCode()) && itemService.isItemCodeExist(requestDTO.getCode())) {
            throw new DuplicateException(CustomExceptionCodes.DUPLICATE_ITEM_CODE.name(), "Duplicate item code: "
                    + requestDTO.getCode());
        }

        return true;
    }

    public boolean isSatisfiedBy(UpdateItemPriceRequestDTO requestDTO, Item item) {

        if (item == null) {
            throw new NotFoundException(CustomExceptionCodes.ITEM_NOT_FOUND.name(), "Item not found");
        }

        if (requestDTO.getPrice() == null) {
            throw new BadRequestException(CustomExceptionCodes.PRICE_REQUIRED.name(), "Item price is required");
        }

        if (requestDTO.getPrice().getBuyingPrice() <= 0 || requestDTO.getPrice().getSellingPrice() <= 0) {
            throw new BadRequestException(CustomExceptionCodes.INVALID_PRICE.name(), "Invalid buying or selling price");
        }

        return true;
    }
}
