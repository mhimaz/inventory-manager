package com.inventory.manager.domain.location;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.inventory.manager.domain.item.ItemStock;
import com.inventory.manager.domain.item.ItemStockService;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepo;

    @Autowired
    private ItemStockService itemStockService;

    public Location createLocation(Location location) {

        location.setIsDeleted(false);
        locationRepo.save(location);

        return location;
    }

    public void deleteLocation(Location location, List<ItemStock> itemStocks) {

        itemStockService.deleteItemStocks(itemStocks);

        location.setIsDeleted(true);
        locationRepo.save(location);
    }

    public void updateLocation(Location location) {

        locationRepo.save(location);
    }

    public Page<Location> findAll(String keyword, Integer page, Integer count, String sortDirection, String sortBy) {

        Pageable pageable = new PageRequest(page, count, Direction.fromString(sortDirection), sortBy);
        Page<Location> locations = locationRepo.findAll(keyword, pageable);
        return locations;
    }

    public List<Location> findByLocationIds(List<Integer> locationIds) {

        return locationRepo.findByLocationIdsAndIsDeleted(locationIds, false);
    }

    public Boolean isLocationNameExist(String name) {

        Location location = locationRepo.findByNameIgnoreCaseAndIsDeleted(name, false);
        return location != null;
    }

    public Location findLocationById(Integer id) {

        return locationRepo.findByIdAndIsDeleted(id, false);
    }
}
