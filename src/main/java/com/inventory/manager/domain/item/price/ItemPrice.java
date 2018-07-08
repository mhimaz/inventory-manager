package com.inventory.manager.domain.item.price;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.inventory.manager.domain.enums.PriceType;
import com.inventory.manager.domain.shared.BaseEntity;

@Entity
@Table(name = "Item_Price")
public class ItemPrice extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8339453613457819118L;

    @Id
    @GenericGenerator(name = "idgenerator", strategy = "increment")
    @GeneratedValue(generator = "idgenerator")
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "SellingPrice")
    private BigDecimal sellingPrice;

    @Column(name = "BuyingPrice")
    private BigDecimal buyingPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "PriceType")
    private PriceType priceType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

}
