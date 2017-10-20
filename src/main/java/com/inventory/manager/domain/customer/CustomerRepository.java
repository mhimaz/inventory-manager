package com.inventory.manager.domain.customer;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Customer findByIdAndIsDeleted(Integer id, Boolean isDeleted);

    @Query("FROM Customer c WHERE c.isDeleted = false AND (c.name LIKE %:keyword% OR c.email LIKE %:keyword%)")
    Page<Customer> findAll(@Param("keyword") String keyword, Pageable page);

    @Query("FROM Customer c WHERE c.isDeleted = :isDeleted AND c.id IN (:ids)")
    List<Customer> findByIdsAndIsDeleted(@Param("ids") Set<Integer> ids, @Param("isDeleted") Boolean isDeleted);

    Customer findByNameAndIsDeleted(String name, Boolean isDeleted);

    List<Customer> findByIsDeleted(Boolean isDeleted);

}
