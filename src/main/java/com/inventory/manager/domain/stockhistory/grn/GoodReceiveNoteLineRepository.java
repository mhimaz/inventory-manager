package com.inventory.manager.domain.stockhistory.grn;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodReceiveNoteLineRepository extends CrudRepository<GoodReceiveNoteLine, Integer> {

}
