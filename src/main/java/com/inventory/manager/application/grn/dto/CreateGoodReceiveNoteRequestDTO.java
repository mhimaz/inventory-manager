package com.inventory.manager.application.grn.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateGoodReceiveNoteRequestDTO {

    private Integer supplierId;

    private Date purchasedDate;

    private String receiptNo;

    private Integer locationId;

    private String remarks;

    private boolean isReturn;

    private List<GoodReceiveNoteLineRequestDTO> goodReceiveNoteLines;

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
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

    public List<GoodReceiveNoteLineRequestDTO> getGoodReceiveNoteLines() {
        if (goodReceiveNoteLines == null) {
            goodReceiveNoteLines = new ArrayList<>();
        }
        return goodReceiveNoteLines;
    }

    public void setGoodReceiveNoteLines(List<GoodReceiveNoteLineRequestDTO> goodReceiveNoteLines) {
        this.goodReceiveNoteLines = goodReceiveNoteLines;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CreateGoodReceiveNoteRequestDTO ");
        sb.append("{supplierId=").append(supplierId);
        sb.append(", purchasedDate=").append(purchasedDate);
        sb.append(", receiptNo=").append(receiptNo);
        sb.append(", locationId=").append(locationId);
        sb.append(", remarks=").append(remarks);
        sb.append(", isReturn=").append(isReturn);
        sb.append(", goodReceiveNoteLines=").append(goodReceiveNoteLines);
        sb.append('}');
        return sb.toString();
    }

}
