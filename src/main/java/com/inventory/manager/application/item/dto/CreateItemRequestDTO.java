package com.inventory.manager.application.item.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inventory.manager.application.item.price.PriceRequestDTO;
import com.inventory.manager.application.shared.dto.LocationQuantityDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateItemRequestDTO {

    private Integer supplierId;

    private String code;

    private String name;

    private List<LocationQuantityDTO> locationQuantities;

    private PriceRequestDTO price;

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
            locationQuantities = new ArrayList<>();
        }
        return locationQuantities;
    }

    public void setLocationQuantities(List<LocationQuantityDTO> locationQuantities) {
        this.locationQuantities = locationQuantities;
    }

    public PriceRequestDTO getPrice() {
        return price;
    }

    public void setPrice(PriceRequestDTO price) {
        this.price = price;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CreateItemRequestDTO ");
        sb.append("{supplierId=").append(supplierId);
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", locationQuantities=").append(locationQuantities);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }

}
