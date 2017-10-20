package com.inventory.manager.application.shared.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationQuantityDTO {

    private String location;

    private Long quantity;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        sb.append("{location=").append(location);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }

}
