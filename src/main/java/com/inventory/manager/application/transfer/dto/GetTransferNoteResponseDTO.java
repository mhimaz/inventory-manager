package com.inventory.manager.application.transfer.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inventory.manager.application.shared.dto.BaseResponseDTO;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class GetTransferNoteResponseDTO extends BaseResponseDTO {

    private Integer id;

    private Date transferredDate;

    private String fromLocation;

    private String toLocation;

    private String remarks;

    private List<TransferNoteLineResponseDTO> lines;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTransferredDate() {
        return transferredDate;
    }

    public void setTransferredDate(Date transferredDate) {
        this.transferredDate = transferredDate;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<TransferNoteLineResponseDTO> getLines() {
        if (lines == null) {
            lines = new ArrayList<TransferNoteLineResponseDTO>();
        }
        return lines;
    }

    public void setLines(List<TransferNoteLineResponseDTO> lines) {
        this.lines = lines;
    }

}
