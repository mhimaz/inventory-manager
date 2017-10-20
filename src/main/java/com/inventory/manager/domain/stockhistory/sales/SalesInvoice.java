package com.inventory.manager.domain.stockhistory.sales;

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

import com.inventory.manager.domain.customer.Customer;
import com.inventory.manager.domain.enums.Location;
import com.inventory.manager.domain.shared.BaseEntity;

@Entity
@Table(name = "Sales_Invoice")
public class SalesInvoice extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 7089644848732787020L;

    @Id
    @GenericGenerator(name = "idgenerator", strategy = "increment")
    @GeneratedValue(generator = "idgenerator")
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID")
    private Customer customer;

    @Column(name = "ReceiptNo")
    private String receiptNo;

    @Column(name = "SoldDate")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime soldDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Location")
    private Location location;

    @Column(name = "Remarks")
    private String remarks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "salesInvoice")
    private List<SalesInvoiceLine> salesInvoiceLines = new ArrayList<SalesInvoiceLine>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public DateTime getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(DateTime soldDate) {
        this.soldDate = soldDate;
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

    public List<SalesInvoiceLine> getSalesInvoiceLines() {
        return salesInvoiceLines;
    }

    public void setSalesInvoiceLines(List<SalesInvoiceLine> salesInvoiceLines) {
        this.salesInvoiceLines = salesInvoiceLines;
    }

}
