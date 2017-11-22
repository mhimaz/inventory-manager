package com.inventory.manager.domain.supplier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepo;

    public Supplier createSupplier(Supplier supplier) {

        supplier.setIsDeleted(false);
        return supplierRepo.save(supplier);

    }

    public Supplier updateSupplier(Supplier supplier) {

        return supplierRepo.save(supplier);
    }

    public Supplier deleteSupplier(Supplier supplier) {

        supplier.setIsDeleted(true);
        return supplierRepo.save(supplier);

    }

    public Supplier findSupplierById(Integer id) {

        return supplierRepo.findByIdAndIsDeleted(id, false);
    }

    public Boolean isSupplierNameExist(String name) {

        Supplier supplier = supplierRepo.findByNameAndIsDeleted(name, false);
        return supplier != null;
    }

    public List<Supplier> getAllSuppliers() {

        return supplierRepo.findByIsDeleted(false);
    }

    public Page<Supplier> findAll(String keyword, Integer page, Integer count, String sortDirection, String sortBy) {

        Pageable pageable = new PageRequest(page, count, Direction.fromString(sortDirection), sortBy);
        Page<Supplier> suppliers = supplierRepo.findAll(keyword, pageable);
        return suppliers;
    }

}
