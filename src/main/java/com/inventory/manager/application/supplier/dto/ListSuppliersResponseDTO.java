package com.inventory.manager.application.supplier.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inventory.manager.application.shared.dto.BaseResponseDTO;
import com.inventory.manager.application.shared.dto.PaginationDTO;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ListSuppliersResponseDTO extends BaseResponseDTO {

    private List<GetSupplierResponseDTO> suppliers;

    private PaginationDTO pagination;

    public List<GetSupplierResponseDTO> getSuppliers() {
        if (suppliers == null) {
            suppliers = new ArrayList<GetSupplierResponseDTO>();
        }
        return suppliers;
    }

    public void setSuppliers(List<GetSupplierResponseDTO> suppliers) {
        this.suppliers = suppliers;
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }

}
