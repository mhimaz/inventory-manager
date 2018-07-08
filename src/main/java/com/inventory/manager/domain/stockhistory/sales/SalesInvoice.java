package com.inventory.manager.domain.stockhistory.sales;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.inventory.manager.domain.customer.Customer;
import com.inventory.manager.domain.location.Location;
//import com.inventory.manager.domain.enums.Location;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LocationID")
    private Location location;

    @Column(name = "GrandTotal")
    private BigDecimal grandTotal;

    @Column(name = "DiscountTotal")
    private BigDecimal discountTotal;

    @Column(name = "NetTotal")
    private BigDecimal netTotal;

    @Column(name = "Remarks")
    private String remarks;

    @Column(name = "IsReturn")
    private Boolean isReturn;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "salesInvoice")
    private List<SalesInvoiceLine> salesInvoiceLines = new ArrayList<>();

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Boolean isReturn) {
        this.isReturn = isReturn;
    }

    public List<SalesInvoiceLine> getSalesInvoiceLines() {
        return salesInvoiceLines;
    }

    public void setSalesInvoiceLines(List<SalesInvoiceLine> salesInvoiceLines) {
        this.salesInvoiceLines = salesInvoiceLines;
    }

}
