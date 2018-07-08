package com.inventory.manager.domain.stockhistory.adjustment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.shared.BaseEntity;

@Entity
@Table(name = "Stock_Adjustment_Line")
public class StockAdjustmentLine extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 5943886580221353219L;

    @Id
    @GenericGenerator(name = "idgenerator", strategy = "increment")
    @GeneratedValue(generator = "idgenerator")
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StockAdjustmentID")
    private StockAdjustment stockAdjustment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ItemID")
    private Item item;

    @Column(name = "Quantity")
    private Long quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StockAdjustment getStockAdjustment() {
        return stockAdjustment;
    }

    public void setStockAdjustment(StockAdjustment stockAdjustment) {
        this.stockAdjustment = stockAdjustment;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

}
