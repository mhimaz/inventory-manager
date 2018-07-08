package com.inventory.manager.application.location;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.internal.util.config.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.inventory.manager.application.location.dto.CreateLocationRequestDTO;
import com.inventory.manager.application.location.dto.UpdateLocationRequestDTO;
import com.inventory.manager.domain.item.ItemStock;
import com.inventory.manager.domain.location.Location;
import com.inventory.manager.domain.location.LocationService;
import com.inventory.manager.exception.BadRequestException;
import com.inventory.manager.exception.NotFoundException;

@Component
public class LocationSpecification {

    @Autowired
    private LocationService locationService;

    public boolean isSatisfiedBy(CreateLocationRequestDTO requestDTO) {

        if (!StringUtils.hasText(requestDTO.getName())) {
            throw new BadRequestException("Location name is required");
        }

        if (locationService.isLocationNameExist(requestDTO.getName())) {
            throw new BadRequestException("Location name already exists");
        }

        return true;

    }

    public boolean isSatisfiedBy(UpdateLocationRequestDTO requestDTO, Location location) {

        if (location == null) {
            throw new NotFoundException("Location not found");
        }

        if (!StringUtils.hasText(requestDTO.getName())) {
            throw new BadRequestException("Location name is required");
        }

        if (!location.getName().equalsIgnoreCase(requestDTO.getName())
                && locationService.isLocationNameExist(requestDTO.getName())) {
            throw new BadRequestException("Location name already exists");
        }

        return true;

    }

    public boolean isDeleteSatisfiedBy(Location location, List<ItemStock> itemStocks) {

        if (location == null) {
            throw new NotFoundException("Location not found");
        }

        boolean stockAvailable = false;
        List<String> itemNames = new ArrayList<>();
        for (ItemStock itemStock : itemStocks) {
            if (itemStock.getQuantity() > 0) {
                stockAvailable = true;
                itemNames.add(itemStock.getItem().getName());

            }
        }

        if (stockAvailable) {
            throw new ConfigurationException("Location cannot be deleted, since it has stock available for the items: "
                    + itemNames);
        }

        return true;

    }

}
