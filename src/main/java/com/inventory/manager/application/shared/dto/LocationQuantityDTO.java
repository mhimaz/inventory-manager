package com.inventory.manager.application.shared.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationQuantityDTO {

    // private String location;

    private Integer locationId;

    private Long quantity;

    // public String getLocation() {
    // return location;
    // }
    //
    // public void setLocation(String location) {
    // this.location = location;
    // }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("LocationQuantityDTO ");
        sb.append("{locationId=").append(locationId);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }

}
