package com.inventory.manager.application.adjustment.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inventory.manager.application.location.dto.GetLocationResponseDTO;
import com.inventory.manager.application.shared.dto.BaseResponseDTO;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class GetStockAdjustmentResponseDTO extends BaseResponseDTO {

    private Integer id;

    private Date adjustedDate;

    // private String location;

    private GetLocationResponseDTO location;

    private String remarks;

    private List<StockAdjustmentLineResponseDTO> lines;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAdjustedDate() {
        return adjustedDate;
    }

    public void setAdjustedDate(Date adjustedDate) {
        this.adjustedDate = adjustedDate;
    }

    public GetLocationResponseDTO getLocation() {
        return location;
    }

    public void setLocation(GetLocationResponseDTO location) {
        this.location = location;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<StockAdjustmentLineResponseDTO> getLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public void setLines(List<StockAdjustmentLineResponseDTO> lines) {
        this.lines = lines;
    }

}
