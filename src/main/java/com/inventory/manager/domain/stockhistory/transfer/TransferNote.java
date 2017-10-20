package com.inventory.manager.domain.stockhistory.transfer;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.inventory.manager.domain.enums.Location;
import com.inventory.manager.domain.shared.BaseEntity;

@Entity
@Table(name = "Transfer_Note")
public class TransferNote extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8285846416423363185L;

    @Id
    @GenericGenerator(name = "idgenerator", strategy = "increment")
    @GeneratedValue(generator = "idgenerator")
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "FromLocation")
    private Location fromLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "ToLocation")
    private Location toLocation;

    @Column(name = "TransferredDate")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime transferredDate;

    @Column(name = "Remarks")
    private String remarks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "transferNote")
    private List<TransferNoteLine> transferNoteLines = new ArrayList<TransferNoteLine>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(Location fromLocation) {
        this.fromLocation = fromLocation;
    }

    public Location getToLocation() {
        return toLocation;
    }

    public void setToLocation(Location toLocation) {
        this.toLocation = toLocation;
    }

    public DateTime getTransferredDate() {
        return transferredDate;
    }

    public void setTransferredDate(DateTime transferredDate) {
        this.transferredDate = transferredDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<TransferNoteLine> getTransferNoteLines() {
        return transferNoteLines;
    }

    public void setTransferNoteLines(List<TransferNoteLine> transferNoteLines) {
        this.transferNoteLines = transferNoteLines;
    }

}
