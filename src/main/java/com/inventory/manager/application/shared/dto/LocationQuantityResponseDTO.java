package com.inventory.manager.application.shared.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inventory.manager.application.location.dto.GetLocationResponseDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationQuantityResponseDTO {

    private GetLocationResponseDTO location;

    private Long quantity;

    public GetLocationResponseDTO getLocation() {
        return location;
    }

    public void setLocation(GetLocationResponseDTO location) {
        this.location = location;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

}
