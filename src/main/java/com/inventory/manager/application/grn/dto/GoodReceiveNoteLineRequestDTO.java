package com.inventory.manager.application.grn.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoodReceiveNoteLineRequestDTO {

    private Integer itemId;

    private Long quantity;

    private String discountMode;

    private double discountValue;

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

    public String getDiscountMode() {
        return discountMode;
    }

    public void setDiscountMode(String discountMode) {
        this.discountMode = discountMode;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("GoodReceiveNoteLineRequestDTO ");
        sb.append("{itemId=").append(itemId);
        sb.append(", quantity=").append(quantity);
        sb.append(", discountMode=").append(discountMode);
        sb.append(", discountValue=").append(discountValue);
        sb.append('}');
        return sb.toString();
    }

}
