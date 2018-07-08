package com.inventory.manager.application.item.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inventory.manager.application.shared.dto.BaseResponseDTO;
import com.inventory.manager.application.shared.dto.PaginationDTO;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ListItemsResponseDTO extends BaseResponseDTO {

    private List<GetItemResponseDTO> items;

    private PaginationDTO pagination;

    public List<GetItemResponseDTO> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<GetItemResponseDTO> items) {
        this.items = items;
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }

}
