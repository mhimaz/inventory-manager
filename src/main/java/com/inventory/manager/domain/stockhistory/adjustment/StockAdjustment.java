package com.inventory.manager.domain.stockhistory.adjustment;

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


import com.inventory.manager.domain.location.Location;
//import com.inventory.manager.domain.enums.Location;
import com.inventory.manager.domain.shared.BaseEntity;

@Entity
@Table(name = "Stock_Adjustment")
public class StockAdjustment extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 5943886580221353219L;

    @Id
    @GenericGenerator(name = "idgenerator", strategy = "increment")
    @GeneratedValue(generator = "idgenerator")
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "Location")
//    private Location location;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LocationID")
    private Location location;

    @Column(name = "AdjustedDate")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime adjustedDate;

    @Column(name = "Remarks")
    private String remarks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stockAdjustment")
    private List<StockAdjustmentLine> stockAdjustmentLines = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public DateTime getAdjustedDate() {
        return adjustedDate;
    }

    public void setAdjustedDate(DateTime adjustedDate) {
        this.adjustedDate = adjustedDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<StockAdjustmentLine> getStockAdjustmentLines() {
        return stockAdjustmentLines;
    }

    public void setStockAdjustmentLines(List<StockAdjustmentLine> stockAdjustmentLines) {
        this.stockAdjustmentLines = stockAdjustmentLines;
    }

}
