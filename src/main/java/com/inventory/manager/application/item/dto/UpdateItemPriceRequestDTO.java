package com.inventory.manager.application.item.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inventory.manager.application.item.price.PriceRequestDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateItemPriceRequestDTO {

    private PriceRequestDTO price;

    public PriceRequestDTO getPrice() {
        return price;
    }

    public void setPrice(PriceRequestDTO price) {
        this.price = price;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("UpdateItemPriceRequestDTO ");
        sb.append("{price=").append(price);
        sb.append('}');
        return sb.toString();
    }

}
