package com.inventory.manager.domain.stockhistory.adjustment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockAdjustmentLineRepository extends CrudRepository<StockAdjustmentLine, Integer> {

}
