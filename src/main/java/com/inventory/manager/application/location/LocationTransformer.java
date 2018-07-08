package com.inventory.manager.application.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.inventory.manager.application.item.ItemTransformer;
import com.inventory.manager.application.location.dto.CreateLocationRequestDTO;
import com.inventory.manager.application.location.dto.GetLocationResponseDTO;
import com.inventory.manager.application.location.dto.ListLocationResponseDTO;
import com.inventory.manager.application.location.dto.UpdateLocationRequestDTO;
import com.inventory.manager.application.shared.dto.PaginationDTO;
import com.inventory.manager.domain.location.Location;

@Component
public class LocationTransformer {

    @Autowired
    private ItemTransformer itemTransformer;

    public Location toLocation(CreateLocationRequestDTO requestDTO) {

        Location location = new Location();
        location.setName(requestDTO.getName());
        location.setAddress(requestDTO.getAddress());
        location.setContactNo(requestDTO.getContactNo());
        return location;
    }

    public Location toLocation(UpdateLocationRequestDTO requestDTO, Location location) {

        location.setName(requestDTO.getName());
        location.setAddress(requestDTO.getAddress());
        location.setContactNo(requestDTO.getContactNo());
        return location;
    }

    public ListLocationResponseDTO toListLocationResponseDTO(Page<Location> locations) {
        ListLocationResponseDTO responseDTO = new ListLocationResponseDTO();

        PaginationDTO paginationDetails = PaginationDTO.getInstance(locations.getTotalElements(), locations.getSize(),
                locations.getTotalPages(), locations.getNumber());
        responseDTO.setPagination(paginationDetails);

        for (Location location : locations.getContent()) {
            responseDTO.getLocations().add(this.toGetLocationResponseDTO(location));
        }

        return responseDTO;
    }

    public GetLocationResponseDTO toGetLocationResponseDTO(Location location) {
        GetLocationResponseDTO responseDTO = new GetLocationResponseDTO();

        responseDTO.setId(location.getId());
        responseDTO.setName(location.getName());
        responseDTO.setAddress(location.getAddress());
        responseDTO.setContactNo(location.getContactNo());
        return responseDTO;
    }
}
