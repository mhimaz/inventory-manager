package com.inventory.manager.application.transfer.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inventory.manager.application.shared.dto.BaseResponseDTO;
import com.inventory.manager.application.shared.dto.PaginationDTO;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ListTransferNoteResponseDTO extends BaseResponseDTO {

    private List<GetTransferNoteResponseDTO> transferNotes;

    private PaginationDTO pagination;

    public List<GetTransferNoteResponseDTO> getTransferNotes() {
        if (transferNotes == null) {
            transferNotes = new ArrayList<GetTransferNoteResponseDTO>();
        }
        return transferNotes;
    }

    public void setTransferNotes(List<GetTransferNoteResponseDTO> transferNotes) {
        this.transferNotes = transferNotes;
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }

}
