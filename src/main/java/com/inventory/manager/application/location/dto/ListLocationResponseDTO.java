package com.inventory.manager.application.location.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inventory.manager.application.shared.dto.BaseResponseDTO;
import com.inventory.manager.application.shared.dto.PaginationDTO;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ListLocationResponseDTO extends BaseResponseDTO {

    private List<GetLocationResponseDTO> locations;

    private PaginationDTO pagination;

    public List<GetLocationResponseDTO> getLocations() {
        if (locations == null) {
            locations = new ArrayList<>();
        }
        return locations;
    }

    public void setLocations(List<GetLocationResponseDTO> locations) {
        this.locations = locations;
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }

}
