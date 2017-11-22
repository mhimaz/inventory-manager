package com.inventory.manager.domain.stockhistory.sales;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesInvoiceRepository extends CrudRepository<SalesInvoice, Integer> {

    @Query("FROM SalesInvoice si WHERE si.isDeleted = false AND (si.receiptNo LIKE %:keyword%)")
    Page<SalesInvoice> findAll(@Param("keyword") String keyword, Pageable page);

    @Query("FROM SalesInvoice si WHERE si.isDeleted = false AND si.customer.id = :customerId AND (si.receiptNo LIKE %:keyword%)")
    Page<SalesInvoice> findAllByCusomer(@Param("customerId") Integer customerId, @Param("keyword") String keyword,
            Pageable page);

    SalesInvoice findByIdAndIsDeleted(Integer id, Boolean isDeleted);

}
