package com.inventory.manager.application.customer.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inventory.manager.application.shared.dto.BaseResponseDTO;
import com.inventory.manager.application.shared.dto.PaginationDTO;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ListCustomersResponseDTO extends BaseResponseDTO {

    private List<GetCustomerResponseDTO> customers;

    private PaginationDTO pagination;

    public List<GetCustomerResponseDTO> getCustomers() {
        if (customers == null) {
            customers = new ArrayList<GetCustomerResponseDTO>();
        }
        return customers;
    }

    public void setCustomers(List<GetCustomerResponseDTO> customers) {
        this.customers = customers;
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }

}
