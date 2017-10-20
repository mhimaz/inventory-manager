package com.inventory.manager.domain.stockhistory.transfer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferNoteRepository extends CrudRepository<TransferNote, Integer> {

    @Query("FROM TransferNote tn WHERE tn.isDeleted = false")
    Page<TransferNote> findAll(Pageable page);

    TransferNote findByIdAndIsDeleted(Integer id, Boolean isDeleted);

}
