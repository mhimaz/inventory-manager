package com.inventory.manager.application.item.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inventory.manager.application.shared.dto.LocationQuantityDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateItemRequestDTO {

    private Integer supplierId;

    private String code;

    private String name;

    List<LocationQuantityDTO> locationQuantities;

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LocationQuantityDTO> getLocationQuantities() {
        if (locationQuantities == null) {
            locationQuantities = new ArrayList<LocationQuantityDTO>();
        }
        return locationQuantities;
    }

    public void setLocationQuantities(List<LocationQuantityDTO> locationQuantities) {
        this.locationQuantities = locationQuantities;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CreateItemRequestDTO ");
        sb.append("{supplierId=").append(supplierId);
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", locationQuantities=").append(locationQuantities);
        sb.append('}');
        return sb.toString();
    }

}
