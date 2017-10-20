package com.inventory.manager.domain.stockhistory.grn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.inventory.manager.domain.enums.Location;
import com.inventory.manager.domain.shared.BaseEntity;
import com.inventory.manager.domain.supplier.Supplier;

@Entity
@Table(name = "Good_Receive_Note")
public class GoodReceiveNote extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 5869350288402086395L;

    @Id
    @GenericGenerator(name = "idgenerator", strategy = "increment")
    @GeneratedValue(generator = "idgenerator")
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SupplierID")
    private Supplier supplier;

    @Column(name = "ReceiptNo")
    private String receiptNo;

    @Column(name = "PurchasedDate")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime purchasedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Location")
    private Location location;

    @Column(name = "Remarks")
    private String remarks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "goodReceiveNote")
    private List<GoodReceiveNoteLine> goodReceiveNoteLines = new ArrayList<GoodReceiveNoteLine>();

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

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public DateTime getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(DateTime purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<GoodReceiveNoteLine> getGoodReceiveNoteLines() {
        return goodReceiveNoteLines;
    }

    public void setGoodReceiveNoteLines(List<GoodReceiveNoteLine> goodReceiveNoteLines) {
        this.goodReceiveNoteLines = goodReceiveNoteLines;
    }

}
