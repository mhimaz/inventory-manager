package com.inventory.manager.application.supplier;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.manager.application.supplier.dto.CreateSupplierRequestDTO;
import com.inventory.manager.application.supplier.dto.ListSuppliersResponseDTO;
import com.inventory.manager.application.supplier.dto.UpdateSupplierRequestDTO;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.item.ItemService;
import com.inventory.manager.domain.supplier.Supplier;
import com.inventory.manager.domain.supplier.SupplierService;
import com.inventory.manager.exception.CustomExceptionCodes;
import com.inventory.manager.exception.NotFoundException;

@Service
public class SupplierApplicationService {

    private static final Logger logger = Logger.getLogger(SupplierApplicationService.class);

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SupplierSpecification supplierSpecification;

    @Autowired
    private SupplierTransformer supplierTransformer;

    @Autowired
    private ItemService itemService;

    @Transactional
    public Integer createSupplier(CreateSupplierRequestDTO requestDTO) {
        logger.info("[ Creating Supplier :: " + requestDTO + " ]");

        supplierSpecification.isSatisfiedBy(requestDTO);

        Supplier supplier = supplierTransformer.toSupplier(requestDTO);

        supplier = supplierService.createSupplier(supplier);

        return supplier.getId();

    }

    @Transactional
    public void updateSupplier(Integer supplierId, UpdateSupplierRequestDTO requestDTO) {
        logger.info("[ Updating Supplier :: supplierId: " + supplierId + ", requestDTO: " + requestDTO + " ]");

        Supplier supplier = supplierService.findSupplierById(supplierId);

        supplierSpecification.isSatisfiedBy(requestDTO, supplier);

        supplier = supplierTransformer.toSupplier(requestDTO, supplier);

        supplierService.updateSupplier(supplier);

    }

    @Transactional
    public ListSuppliersResponseDTO getAllSuppliers() {
        logger.info("[ Get All Suppliers ]");
        ListSuppliersResponseDTO responseDTO = new ListSuppliersResponseDTO();

        List<Supplier> suppliers = supplierService.getAllSuppliers();

        suppliers.forEach(supplier -> {
            responseDTO.getSuppliers().add(supplierTransformer.toGetSupplierResponseDTO(supplier));
        });

        return responseDTO;
    }

    @Transactional
    public ListSuppliersResponseDTO listSuppliers(String keyword, int page, int count, String sortDirection,
            String sortBy) {
        logger.info("[ List Suppliers ]");
        Page<Supplier> suppliers = supplierService.findAll(keyword, page, count, sortDirection, sortBy);

        return supplierTransformer.toListSuppliersResponseDTO(suppliers);
    }

    @Transactional
    public void deleteSupplier(Integer supplierId) {
        logger.info("[ Delete Supplier :: supplierId:" + supplierId + "]");

        Supplier supplier = supplierService.findSupplierById(supplierId);

        if (supplier == null) {
            throw new NotFoundException(CustomExceptionCodes.SUPPLIER_NOT_FOUND.name(), "Supplier not found");
        }

        List<Item> items = itemService.findBySupplier(supplier);

        if (!items.isEmpty()) {
            for (Item item : items) {
                itemService.deleteItem(item);
            }
        }
        supplierService.deleteSupplier(supplier);

    }

}
