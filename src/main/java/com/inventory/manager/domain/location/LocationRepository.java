package com.inventory.manager.domain.location;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer> {

    Location findByNameIgnoreCaseAndIsDeleted(String name, Boolean IsDeleted);

    Location findByIdAndIsDeleted(Integer id, Boolean IsDeleted);

    @Query("FROM Location l WHERE l.isDeleted = false AND (l.name LIKE %:keyword% OR l.address LIKE %:keyword% OR l.contactNo LIKE %:keyword%)")
    Page<Location> findAll(@Param("keyword") String keyword, Pageable page);

    @Query("FROM Location l WHERE l.isDeleted = :isDeleted AND l.id IN (:ids)")
    List<Location> findByLocationIdsAndIsDeleted(@Param("ids") List<Integer> ids, @Param("isDeleted") Boolean isDeleted);

}
