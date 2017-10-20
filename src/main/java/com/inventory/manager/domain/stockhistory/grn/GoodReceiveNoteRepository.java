package com.inventory.manager.domain.stockhistory.grn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodReceiveNoteRepository extends CrudRepository<GoodReceiveNote, Integer> {

    @Query("FROM GoodReceiveNote grn WHERE grn.isDeleted = false AND (grn.receiptNo LIKE %:keyword%)")
    Page<GoodReceiveNote> findAll(@Param("keyword") String keyword, Pageable page);

    @Query("FROM GoodReceiveNote grn WHERE grn.isDeleted = false AND grn.supplier.id = :supplierId AND (grn.receiptNo LIKE %:keyword%)")
    Page<GoodReceiveNote> findAllBySupplier(@Param("supplierId") Integer supplierId, @Param("keyword") String keyword,
            Pageable page);

    GoodReceiveNote findByIdAndIsDeleted(Integer id, Boolean isDeleted);

}
