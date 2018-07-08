package com.inventory.manager.application.sales;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.inventory.manager.application.sales.dto.CreateSalesInvoiceRequestDTO;
import com.inventory.manager.application.sales.dto.SalesInvoiceLineRequestDTO;
import com.inventory.manager.domain.enums.DiscountMode;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.item.ItemService;
import com.inventory.manager.domain.location.Location;
import com.inventory.manager.exception.BadRequestException;
import com.inventory.manager.exception.CustomExceptionCodes;
import com.inventory.manager.exception.NotFoundException;

@Component
public class SalesInvoiceSpecification {

    @Autowired
    private ItemService itemService;

    public boolean isSatisfiedBy(CreateSalesInvoiceRequestDTO requestDTO, List<Item> items, List<Integer> itemIds,
            Location location) {

        if (items.size() != itemIds.size()) {
            throw new NotFoundException("Item not found for one of the item ids: " + itemIds);
        }

        if (location == null) {
            throw new NotFoundException("Location not found for the id: " + requestDTO.getLocationId());
        }

        for (SalesInvoiceLineRequestDTO line : requestDTO.getSalesInvoiceLines()) {

            if ((line.getDiscountValue() > 0 && !StringUtils.hasText(line.getDiscountMode()))) {
                throw new BadRequestException(CustomExceptionCodes.DISCOUNT_MODE_REQUIRED.toString(),
                        "Discount mode required");
            }
            if (StringUtils.hasText(line.getDiscountMode()) && line.getDiscountValue() <= 0) {
                throw new BadRequestException(CustomExceptionCodes.DISCOUNT_VALUE_REQUIRED.toString(),
                        "Discount value required");
            }

            if (StringUtils.hasText(line.getDiscountMode())) {

                DiscountMode discountMode = null;
                try {
                    discountMode = DiscountMode.valueOf(line.getDiscountMode().toUpperCase(Locale.ENGLISH));
                } catch (Exception e) {
                    throw new BadRequestException(CustomExceptionCodes.INVALID_DISCOUNT_MODE.toString(),
                            "Invalid discount mode: " + line.getDiscountMode());
                }

                if (DiscountMode.PERCENTAGE == discountMode && line.getDiscountValue() > 100) {
                    throw new BadRequestException(CustomExceptionCodes.INVALID_DISCOUNT_VALUE.toString(),
                            "Invalid discount percentage: " + line.getDiscountValue()
                                    + ". Discount percentage should be between 1 - 100");
                }
            }

            if (requestDTO.isReturn() && line.getQuantity() >= 0) {
                throw new BadRequestException(CustomExceptionCodes.INVALID_QUANTITY.toString(),
                        "Quantity should be negative on return Sales");
            }

        }

        return true;

    }

}
