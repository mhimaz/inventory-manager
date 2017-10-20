package com.inventory.manager.domain.stockhistory.transfer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferNoteLineRepository extends CrudRepository<TransferNoteLine, Integer> {

}
