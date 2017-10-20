package com.inventory.manager.application.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inventory.manager.application.supplier.dto.CreateSupplierRequestDTO;
import com.inventory.manager.application.supplier.dto.UpdateSupplierRequestDTO;
import com.inventory.manager.domain.supplier.Supplier;
import com.inventory.manager.domain.supplier.SupplierService;
import com.inventory.manager.exception.CustomExceptionCodes;
import com.inventory.manager.exception.DuplicateException;
import com.inventory.manager.exception.NotFoundException;

@Component
public class SupplierSpecification {

    @Autowired
    private SupplierService supplierService;

    public boolean isSatisfiedBy(CreateSupplierRequestDTO requestDTO) {

        if (supplierService.isSupplierNameExist(requestDTO.getName())) {
            throw new DuplicateException(CustomExceptionCodes.DUPLICATE_SUPPLIER_NAME.name(),
                    "Duplicate supplier name: " + requestDTO.getName());
        }

        return true;
    }

    public boolean isSatisfiedBy(UpdateSupplierRequestDTO requestDTO, Supplier supplier) {

        if (supplier == null) {
            throw new NotFoundException(CustomExceptionCodes.SUPPLIER_NOT_FOUND.name(), "Supplier not found");
        }
        if (!supplier.getName().equals(requestDTO.getName())
                && supplierService.isSupplierNameExist(requestDTO.getName())) {
            throw new DuplicateException(CustomExceptionCodes.DUPLICATE_SUPPLIER_NAME.name(),
                    "Duplicate supplier name: " + requestDTO.getName());
        }

        return true;
    }

}
