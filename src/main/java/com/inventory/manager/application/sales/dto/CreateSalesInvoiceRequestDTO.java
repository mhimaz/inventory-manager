package com.inventory.manager.application.sales.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateSalesInvoiceRequestDTO {

    private Integer customerId;

    private Date soldDate;

    private String receiptNo;

    private Integer locationId;

    private String remarks;

    private boolean isReturn;

    private List<SalesInvoiceLineRequestDTO> salesInvoiceLines;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Date getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Date soldDate) {
        this.soldDate = soldDate;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean isReturn) {
        this.isReturn = isReturn;
    }

    public List<SalesInvoiceLineRequestDTO> getSalesInvoiceLines() {
        if (salesInvoiceLines == null) {
            salesInvoiceLines = new ArrayList<>();
        }
        return salesInvoiceLines;
    }

    public void setSalesInvoiceLines(List<SalesInvoiceLineRequestDTO> salesInvoiceLines) {
        this.salesInvoiceLines = salesInvoiceLines;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CreateSalesInvoiceRequestDTO ");
        sb.append("{customerId=").append(customerId);
        sb.append(", soldDate=").append(soldDate);
        sb.append(", receiptNo=").append(receiptNo);
        sb.append(", locationId=").append(locationId);
        sb.append(", remarks=").append(remarks);
        sb.append(", isReturn=").append(isReturn);
        sb.append(", salesInvoiceLines=").append(salesInvoiceLines);
        sb.append('}');
        return sb.toString();
    }

}
