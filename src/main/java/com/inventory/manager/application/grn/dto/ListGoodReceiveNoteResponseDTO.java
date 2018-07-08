package com.inventory.manager.application.grn.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inventory.manager.application.shared.dto.BaseResponseDTO;
import com.inventory.manager.application.shared.dto.PaginationDTO;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ListGoodReceiveNoteResponseDTO extends BaseResponseDTO {

    private List<GetGoodReceiveNoteResponseDTO> grns;

    private PaginationDTO pagination;

    public List<GetGoodReceiveNoteResponseDTO> getGrns() {
        if (grns == null) {
            grns = new ArrayList<>();
        }
        return grns;
    }

    public void setGrns(List<GetGoodReceiveNoteResponseDTO> grns) {
        this.grns = grns;
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }

}
