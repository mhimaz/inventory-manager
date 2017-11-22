package com.inventory.manager.application.shared.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BaseResponseDTO {

    private List<DTOError> errors;

    public List<DTOError> getErrors() {
        return errors;
    }

    public void setErrors(List<DTOError> errors) {
        this.errors = errors;
    }

    public BaseResponseDTO addError(String name, String desc) {
        if (errors == null)
            errors = new ArrayList<DTOError>();

        errors.add(new DTOError(name, desc));

        return this;
    }

}
