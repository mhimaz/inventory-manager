package com.inventory.manager.application.transfer.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTransferNoteRequestDTO {

    private Date transferredDate;

    // private String fromLocation;
    //
    // private String toLocation;

    private Integer fromLocationId;

    private Integer toLocationId;

    private String remarks;

    private List<TransferNoteLineRequestDTO> transferNoteLines;

    public Date getTransferredDate() {
        return transferredDate;
    }

    public void setTransferredDate(Date transferredDate) {
        this.transferredDate = transferredDate;
    }

    // public String getFromLocation() {
    // return fromLocation;
    // }
    //
    // public void setFromLocation(String fromLocation) {
    // this.fromLocation = fromLocation;
    // }
    //
    // public String getToLocation() {
    // return toLocation;
    // }
    //
    // public void setToLocation(String toLocation) {
    // this.toLocation = toLocation;
    // }

    public Integer getFromLocationId() {
        return fromLocationId;
    }

    public void setFromLocationId(Integer fromLocationId) {
        this.fromLocationId = fromLocationId;
    }

    public Integer getToLocationId() {
        return toLocationId;
    }

    public void setToLocationId(Integer toLocationId) {
        this.toLocationId = toLocationId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<TransferNoteLineRequestDTO> getTransferNoteLines() {
        if (transferNoteLines == null) {
            transferNoteLines = new ArrayList<TransferNoteLineRequestDTO>();
        }
        return transferNoteLines;
    }

    public void setTransferNoteLines(List<TransferNoteLineRequestDTO> transferNoteLines) {
        this.transferNoteLines = transferNoteLines;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CreateTransferNoteRequestDTO ");
        sb.append("{transferredDate=").append(transferredDate);
        sb.append(", fromLocationId=").append(fromLocationId);
        sb.append(", toLocationId=").append(toLocationId);
        sb.append(", remarks=").append(remarks);
        sb.append(", transferNoteLines=").append(transferNoteLines);
        sb.append('}');
        return sb.toString();
    }

}
