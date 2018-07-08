package com.inventory.manager.domain.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.inventory.manager.domain.item.price.ItemPrice;
import com.inventory.manager.domain.shared.BaseEntity;
import com.inventory.manager.domain.supplier.Supplier;

@Entity
@Table(name = "Item")
public class Item extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -2209231757612831531L;

    @Id
    @GenericGenerator(name = "idgenerator", strategy = "increment")
    @GeneratedValue(generator = "idgenerator")
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SupplierID")
    private Supplier supplier;

    @Column(name = "Code")
    private String code;

    @Column(name = "Name")
    private String name;

    @OneToOne
    @JoinColumn(name = "PriceID")
    private ItemPrice price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    private List<ItemStock> itemStocks = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemPrice getPrice() {
        return price;
    }

    public void setPrice(ItemPrice price) {
        this.price = price;
    }

    public List<ItemStock> getItemStocks() {
        return itemStocks;
    }

    public void setItemStocks(List<ItemStock> itemStocks) {
        this.itemStocks = itemStocks;
    }

}
