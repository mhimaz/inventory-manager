package com.inventory.manager.application.adjustment.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inventory.manager.application.shared.dto.BaseResponseDTO;
import com.inventory.manager.application.shared.dto.PaginationDTO;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ListStockAdjustmentResponseDTO extends BaseResponseDTO {

    private List<GetStockAdjustmentResponseDTO> stockAdjustments;

    private PaginationDTO pagination;

    public List<GetStockAdjustmentResponseDTO> getStockAdjustments() {
        if (stockAdjustments == null) {
            stockAdjustments = new ArrayList<>();
        }
        return stockAdjustments;
    }

    public void setStockAdjustments(List<GetStockAdjustmentResponseDTO> stockAdjustments) {
        this.stockAdjustments = stockAdjustments;
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }

}
