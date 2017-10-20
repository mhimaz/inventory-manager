package com.inventory.manager.domain.stockhistory.transfer;

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
@Table(name = "Transfer_Note_Line")
public class TransferNoteLine extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8358066857872267472L;

    @Id
    @GenericGenerator(name = "idgenerator", strategy = "increment")
    @GeneratedValue(generator = "idgenerator")
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TransferNoteID")
    private TransferNote transferNote;

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

    public TransferNote getTransferNote() {
        return transferNote;
    }

    public void setTransferNote(TransferNote transferNote) {
        this.transferNote = transferNote;
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
