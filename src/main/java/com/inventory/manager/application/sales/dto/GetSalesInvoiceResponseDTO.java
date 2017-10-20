package com.inventory.manager.application.sales.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inventory.manager.application.customer.dto.GetCustomerResponseDTO;
import com.inventory.manager.application.shared.dto.BaseResponseDTO;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class GetSalesInvoiceResponseDTO extends BaseResponseDTO {

    private Integer id;

    private GetCustomerResponseDTO customer;

    private String receiptNo;

    private Date soldDate;

    private String location;

    private String remarks;

    private List<SalesInvoiceLineResponseDTO> lines;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GetCustomerResponseDTO getCustomer() {
        return customer;
    }

    public void setCustomer(GetCustomerResponseDTO customer) {
        this.customer = customer;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Date getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Date soldDate) {
        this.soldDate = soldDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<SalesInvoiceLineResponseDTO> getLines() {
        if (lines == null) {
            lines = new ArrayList<SalesInvoiceLineResponseDTO>();
        }
        return lines;
    }

    public void setLines(List<SalesInvoiceLineResponseDTO> lines) {
        this.lines = lines;
    }

}
