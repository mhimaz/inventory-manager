package com.inventory.manager.domain.item;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inventory.manager.domain.supplier.Supplier;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {

    List<Item> findBySupplierAndIsDeleted(Supplier supplier, Boolean isDeleted);

    @Query("FROM Item i WHERE i.isDeleted = false AND (i.name LIKE %:keyword% OR i.code LIKE %:keyword%)")
    Page<Item> findAll(@Param("keyword") String keyword, Pageable page);

    @Query("FROM Item i WHERE i.isDeleted = false AND i.supplier.id = :supplierId AND (i.name LIKE %:keyword% OR i.code LIKE %:keyword%)")
    Page<Item> findAllBySupplier(@Param("supplierId") Integer supplierId, @Param("keyword") String keyword,
            Pageable page);

    @Query("FROM Item i WHERE i.isDeleted = :isDeleted AND i.id IN (:ids) AND i.supplier.id = :supplierId")
    List<Item> findBySupplierIdAndItemIdsAndIsDeleted(@Param("supplierId") Integer supplierId,
            @Param("ids") List<Integer> ids, @Param("isDeleted") Boolean isDeleted);

    @Query("FROM Item i WHERE i.isDeleted = :isDeleted AND i.id IN (:ids)")
    List<Item> findByItemIdsAndIsDeleted(@Param("ids") List<Integer> ids, @Param("isDeleted") Boolean isDeleted);

    Item findByCodeAndIsDeleted(String code, Boolean IsDeleted);

    Item findByNameAndIsDeleted(String name, Boolean IsDeleted);
    
    Item findByIdAndIsDeleted(Integer id, Boolean IsDeleted);

}
