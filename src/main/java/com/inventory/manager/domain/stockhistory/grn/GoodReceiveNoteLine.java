package com.inventory.manager.domain.stockhistory.grn;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.inventory.manager.domain.enums.DiscountMode;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.shared.BaseEntity;

@Entity
@Table(name = "Good_Receive_Note_Line")
public class GoodReceiveNoteLine extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 802188386464510744L;

    @Id
    @GenericGenerator(name = "idgenerator", strategy = "increment")
    @GeneratedValue(generator = "idgenerator")
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GoodReceiveNoteID")
    private GoodReceiveNote goodReceiveNote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ItemID")
    private Item item;

    @Column(name = "Quantity")
    private Long quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "DiscountMode")
    private DiscountMode discountMode;

    @Column(name = "DiscountValue")
    private BigDecimal discountValue;

    @Column(name = "GrandTotal")
    private BigDecimal grandTotal;

    @Column(name = "DiscountTotal")
    private BigDecimal discountTotal;

    @Column(name = "NetTotal")
    private BigDecimal netTotal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GoodReceiveNote getGoodReceiveNote() {
        return goodReceiveNote;
    }

    public void setGoodReceiveNote(GoodReceiveNote goodReceiveNote) {
        this.goodReceiveNote = goodReceiveNote;
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

    public DiscountMode getDiscountMode() {
        return discountMode;
    }

    public void setDiscountMode(DiscountMode discountMode) {
        this.discountMode = discountMode;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public BigDecimal getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(BigDecimal discountTotal) {
        this.discountTotal = discountTotal;
    }

    public BigDecimal getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(BigDecimal netTotal) {
        this.netTotal = netTotal;
    }

}
