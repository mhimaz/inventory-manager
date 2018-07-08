package com.inventory.manager.application.grn.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inventory.manager.application.location.dto.GetLocationResponseDTO;
import com.inventory.manager.application.shared.dto.BaseResponseDTO;
import com.inventory.manager.application.supplier.dto.GetSupplierResponseDTO;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class GetGoodReceiveNoteResponseDTO extends BaseResponseDTO {

    private Integer id;

    private GetSupplierResponseDTO supplier;

    private String receiptNo;

    private Date purchasedDate;

    private GetLocationResponseDTO location;

    private String remarks;

    private Boolean isReturn;

    private double grandTotal;

    private double discountTotal;

    private double netTotal;

    private List<GoodReceiveNoteLineResponseDTO> lines;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GetSupplierResponseDTO getSupplier() {
        return supplier;
    }

    public void setSupplier(GetSupplierResponseDTO supplier) {
        this.supplier = supplier;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public GetLocationResponseDTO getLocation() {
        return location;
    }

    public void setLocation(GetLocationResponseDTO location) {
        this.location = location;
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

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public double getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(double discountTotal) {
        this.discountTotal = discountTotal;
    }

    public double getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(double netTotal) {
        this.netTotal = netTotal;
    }

    public List<GoodReceiveNoteLineResponseDTO> getLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public void setLines(List<GoodReceiveNoteLineResponseDTO> lines) {
        this.lines = lines;
    }

}
