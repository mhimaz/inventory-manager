package com.inventory.manager.application.transfer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferNoteLineRequestDTO {

    private Integer itemId;

    private Long quantity;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("TransferNoteLineRequestDTO ");
        sb.append("{itemId=").append(itemId);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }

}
