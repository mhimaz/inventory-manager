package com.inventory.manager.domain.stockhistory.sales;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesInvoiceLineRepository extends CrudRepository<SalesInvoiceLine, Integer> {

}
