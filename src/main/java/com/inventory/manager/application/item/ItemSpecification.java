package com.inventory.manager.application.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inventory.manager.application.item.dto.CreateItemRequestDTO;
import com.inventory.manager.application.item.dto.UpdateItemRequestDTO;
import com.inventory.manager.application.shared.dto.LocationQuantityDTO;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.item.ItemService;
import com.inventory.manager.domain.supplier.Supplier;
import com.inventory.manager.exception.BadRequestException;
import com.inventory.manager.exception.CustomExceptionCodes;
import com.inventory.manager.exception.DuplicateException;
import com.inventory.manager.exception.NotFoundException;

@Component
public class ItemSpecification {

    @Autowired
    private ItemService itemService;

    public boolean isSatisfiedBy(CreateItemRequestDTO requestDTO, Supplier supplier) {

        if (supplier == null) {
            throw new NotFoundException(CustomExceptionCodes.SUPPLIER_NOT_FOUND.name(),
                    "Supplier not found for the id: " + requestDTO.getSupplierId());
        }

        if (requestDTO.getCode() != null && itemService.isItemCodeExist(requestDTO.getCode())) {
            throw new DuplicateException(CustomExceptionCodes.DUPLICATE_ITEM_CODE.name(), "Duplicate item code: "
                    + requestDTO.getCode());
        }

        if (itemService.isItemNameExist(requestDTO.getName())) {
            throw new DuplicateException(CustomExceptionCodes.DUPLICATE_ITEM_NAME.name(), "Duplicate item name: "
                    + requestDTO.getName());
        }

        if (!requestDTO.getLocationQuantities().isEmpty()) {
            // check if a location exists more than once
            requestDTO.getLocationQuantities().forEach(
                    lq -> {
                        if (requestDTO.getLocationQuantities().stream().map(LocationQuantityDTO::getLocation)
                                .filter(lq.getLocation()::equals).count() > 1) {
                            throw new DuplicateException(CustomExceptionCodes.DUPLICATE_LOCATION.name(),
                                    "Duplicate location found: " + lq.getLocation());

                        } else if (lq.getQuantity() <= 0) {
                            throw new BadRequestException(CustomExceptionCodes.INVALID_QUANTITY.name(),
                                    "Invalid quantity: " + lq.getQuantity());
                        }
                    });
        }

        return true;
    }

    public boolean isSatisfiedBy(UpdateItemRequestDTO requestDTO, Item item) {

        if (item == null) {
            throw new NotFoundException(CustomExceptionCodes.ITEM_NOT_FOUND.name(), "Item not found");
        }

        if (item.getName() == null) {
            throw new BadRequestException(CustomExceptionCodes.ITEM_NAME_REQUIRED.name(), "Item name is required");
        }

        if (item.getCode() == null) {
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
}
