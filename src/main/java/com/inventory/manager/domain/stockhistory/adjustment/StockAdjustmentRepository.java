package com.inventory.manager.domain.stockhistory.adjustment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockAdjustmentRepository extends CrudRepository<StockAdjustment, Integer> {

    @Query("FROM StockAdjustment sa WHERE sa.isDeleted = false")
    Page<StockAdjustment> findAll(Pageable page);

    @Query("FROM StockAdjustment sa WHERE sa.isDeleted = false AND (sa.remarks LIKE %:keyword%)")
    Page<StockAdjustment> findAll(@Param("keyword") String keyword, Pageable page);

    StockAdjustment findByIdAndIsDeleted(Integer id, Boolean isDeleted);

}
