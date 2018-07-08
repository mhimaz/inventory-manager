package com.inventory.manager.application.item.price;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inventory.manager.application.shared.dto.BaseResponseDTO;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PriceResponseDTO extends BaseResponseDTO {

    private Integer id;

    private double sellingPrice;

    private double buyingPrice;

    private String priceType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

}
