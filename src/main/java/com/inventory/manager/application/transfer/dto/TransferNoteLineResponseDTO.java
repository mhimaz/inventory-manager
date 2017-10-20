package com.inventory.manager.application.transfer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inventory.manager.application.item.dto.GetItemResponseDTO;
import com.inventory.manager.application.shared.dto.BaseResponseDTO;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TransferNoteLineResponseDTO extends BaseResponseDTO {

    private Integer id;

    private GetItemResponseDTO item;

    private Long quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GetItemResponseDTO getItem() {
        return item;
    }

    public void setItem(GetItemResponseDTO item) {
        this.item = item;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

}
