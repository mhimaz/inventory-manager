package com.inventory.manager.application.supplier;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.inventory.manager.application.shared.dto.PaginationDTO;
import com.inventory.manager.application.supplier.dto.CreateSupplierRequestDTO;
import com.inventory.manager.application.supplier.dto.GetSupplierResponseDTO;
import com.inventory.manager.application.supplier.dto.ListSuppliersResponseDTO;
import com.inventory.manager.application.supplier.dto.UpdateSupplierRequestDTO;
import com.inventory.manager.domain.supplier.Supplier;

@Component
public class SupplierTransformer {

    public Supplier toSupplier(CreateSupplierRequestDTO requestDTO) {
        Supplier supplier = new Supplier();
        supplier.setName(requestDTO.getName());
        supplier.setContactNo(requestDTO.getContactNo());
        supplier.setEmail(requestDTO.getEmail());
        return supplier;
    }

    public Supplier toSupplier(UpdateSupplierRequestDTO requestDTO, Supplier supplier) {
        supplier.setName(requestDTO.getName());
        supplier.setContactNo(requestDTO.getContactNo());
        supplier.setEmail(requestDTO.getEmail());
        return supplier;
    }

    public GetSupplierResponseDTO toGetSupplierResponseDTO(Supplier supplier) {
        GetSupplierResponseDTO supplierDTO = new GetSupplierResponseDTO();
        supplierDTO.setId(supplier.getId());
        supplierDTO.setName(supplier.getName());
        supplierDTO.setContactNo(supplier.getContactNo());
        supplierDTO.setEmail(supplier.getEmail());
        return supplierDTO;
    }

    public ListSuppliersResponseDTO toListSuppliersResponseDTO(Page<Supplier> suppliers) {
        ListSuppliersResponseDTO responseDTO = new ListSuppliersResponseDTO();

        PaginationDTO paginationDetails = PaginationDTO.getInstance(suppliers.getTotalElements(), suppliers.getSize(),
                suppliers.getTotalPages(), suppliers.getNumber());
        responseDTO.setPagination(paginationDetails);

        for (Supplier supplier : suppliers.getContent()) {
            responseDTO.getSuppliers().add(this.toGetSupplierResponseDTO(supplier));
        }

        return responseDTO;
    }

}
