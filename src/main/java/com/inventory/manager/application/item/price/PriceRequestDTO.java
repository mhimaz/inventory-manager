package com.inventory.manager.application.item.price;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceRequestDTO {

    private double sellingPrice;

    private double buyingPrice;

    private String priceType;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PriceRequestDTO ");
        sb.append("{sellingPrice=").append(sellingPrice);
        sb.append(", buyingPrice=").append(buyingPrice);
        sb.append(", priceType=").append(priceType);
        sb.append('}');
        return sb.toString();
    }

}
