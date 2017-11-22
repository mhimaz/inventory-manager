package com.inventory.manager.domain.supplier;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Integer> {

    Supplier findByIdAndIsDeleted(Integer id, Boolean isDeleted);

    @Query("FROM Supplier s WHERE s.isDeleted = false AND (s.name LIKE %:keyword% OR s.email LIKE %:keyword%)")
    Page<Supplier> findAll(@Param("keyword") String keyword, Pageable page);

    @Query("FROM Supplier s WHERE s.isDeleted = :isDeleted AND s.id IN (:ids)")
    List<Supplier> findByIdsAndIsDeleted(@Param("ids") Set<Integer> ids, @Param("isDeleted") Boolean isDeleted);

    Supplier findByNameAndIsDeleted(String name, Boolean isDeleted);

    List<Supplier> findByIsDeleted(Boolean isDeleted);

}
