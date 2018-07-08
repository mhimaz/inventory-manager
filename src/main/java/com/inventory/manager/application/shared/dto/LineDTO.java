package com.inventory.manager.application.shared.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inventory.manager.application.item.dto.GetItemResponseDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LineDTO {

    private GetItemResponseDTO item;

    private Long quantity;

    private String discountMode;

    private double discountValue;

    // below 3 properties will only be set to CalculateTotalResponseDTO
    private double grandTotal;

    private double discountTotal;

    private double netTotal;

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

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public double getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(double discountTotal) {
        this.discountTotal = discountTotal;
    }

    public double getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(double netTotal) {
        this.netTotal = netTotal;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("LineDTO ");
        sb.append("{item.id=").append(item.getId());
        sb.append(", quantity=").append(quantity);
        sb.append(", discountMode=").append(discountMode);
        sb.append(", discountValue=").append(discountValue);
        sb.append(", grandTotal=").append(grandTotal);
        sb.append(", discountTotal=").append(discountTotal);
        sb.append(", netTotal=").append(netTotal);
        sb.append('}');
        return sb.toString();
    }

}
