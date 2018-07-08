package com.inventory.manager.application.location;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.manager.application.location.dto.CreateLocationRequestDTO;
import com.inventory.manager.application.location.dto.GetLocationResponseDTO;
import com.inventory.manager.application.location.dto.ListLocationResponseDTO;
import com.inventory.manager.application.location.dto.UpdateLocationRequestDTO;
import com.inventory.manager.domain.item.ItemStock;
import com.inventory.manager.domain.item.ItemStockService;
import com.inventory.manager.domain.location.Location;
import com.inventory.manager.domain.location.LocationService;
import com.inventory.manager.exception.NotFoundException;

@Service
public class LocationApplicationService {

    private static final Logger logger = Logger.getLogger(LocationApplicationService.class);

    @Autowired
    private LocationService locationService;

    @Autowired
    private ItemStockService itemStockService;

    @Autowired
    private LocationSpecification locationSpec;

    @Autowired
    private LocationTransformer locationTransformer;

    @Transactional
    public Integer createLocation(CreateLocationRequestDTO requestDTO) {
        logger.info("[ Create Location :: " + requestDTO + " ]");

        locationSpec.isSatisfiedBy(requestDTO);

        Location location = locationTransformer.toLocation(requestDTO);

        location = locationService.createLocation(location);

        return location.getId();
    }

    @Transactional
    public void updateLocation(Integer locationId, UpdateLocationRequestDTO requestDTO) {
        logger.info("[ Update Location :: locationId: " + locationId + ", requestDTO: " + requestDTO + " ]");

        Location location = locationService.findLocationById(locationId);

        locationSpec.isSatisfiedBy(requestDTO, location);

        location = locationTransformer.toLocation(requestDTO, location);

        locationService.updateLocation(location);

    }

    @Transactional
    public void deleteLocation(Integer locationId) {
        logger.info("[ Delete Location :: locationId: " + locationId + " ]");

        Location location = locationService.findLocationById(locationId);

        List<ItemStock> itemStocks = null;
        if (location != null) {
            itemStocks = itemStockService.findItemStock(location);
        }

        locationSpec.isDeleteSatisfiedBy(location, itemStocks);

        locationService.deleteLocation(location, itemStocks);

    }

    @Transactional
    public ListLocationResponseDTO listLocations(String keyword, int page, int count, String sortDirection,
            String sortBy) {

        Page<Location> locations = locationService.findAll(keyword, page, count, sortDirection, sortBy);

        return locationTransformer.toListLocationResponseDTO(locations);
    }

    @Transactional
    public GetLocationResponseDTO getLocation(Integer id) {

        Location location = locationService.findLocationById(id);
        if (location == null) {
            throw new NotFoundException("Location not found for the id: " + id);
        }
        return locationTransformer.toGetLocationResponseDTO(location);
    }
}
