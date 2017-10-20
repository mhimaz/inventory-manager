package com.inventory.manager.domain.stockhistory.grn;

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

}
