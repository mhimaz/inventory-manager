package com.inventory.manager.application.sales.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inventory.manager.application.shared.dto.BaseResponseDTO;
import com.inventory.manager.application.shared.dto.PaginationDTO;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ListSalesInvoiceResponseDTO extends BaseResponseDTO {

    private List<GetSalesInvoiceResponseDTO> salesInvoices;

    private PaginationDTO pagination;

    public List<GetSalesInvoiceResponseDTO> getSalesInvoices() {
        if (salesInvoices == null) {
            salesInvoices = new ArrayList<GetSalesInvoiceResponseDTO>();
        }
        return salesInvoices;
    }

    public void setSalesInvoices(List<GetSalesInvoiceResponseDTO> salesInvoices) {
        this.salesInvoices = salesInvoices;
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }

}
