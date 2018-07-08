package com.inventory.manager.application.adjustment.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateStockAdjustmentRequestDTO {

    // private String location;

    private Integer locationId;

    private String remarks;

    private List<StockAdjustmentLineRequestDTO> stockAdjustmentLines;

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<StockAdjustmentLineRequestDTO> getStockAdjustmentLines() {
        if (stockAdjustmentLines == null) {
            stockAdjustmentLines = new ArrayList<>();
        }
        return stockAdjustmentLines;
    }

    public void setStockAdjustmentLines(List<StockAdjustmentLineRequestDTO> stockAdjustmentLines) {
        this.stockAdjustmentLines = stockAdjustmentLines;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CreateStockAdjustmentRequestDTO ");
        sb.append(", locationId=").append(locationId);
        sb.append(", remarks=").append(remarks);
        sb.append(", stockAdjustmentLines=").append(stockAdjustmentLines);
        sb.append('}');
        return sb.toString();
    }

}
