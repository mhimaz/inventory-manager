package com.inventory.manager.application.shared.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CalculateTotalRequestDTO {

    private List<LineDTO> lines;

    public List<LineDTO> getLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public void setLines(List<LineDTO> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CalculateTotalRequestDTO ");
        sb.append("{lines=").append(lines);
        sb.append('}');
        return sb.toString();
    }

}
