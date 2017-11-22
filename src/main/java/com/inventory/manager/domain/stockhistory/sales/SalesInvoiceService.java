package com.inventory.manager.domain.stockhistory.sales;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.inventory.manager.domain.item.ItemStock;
import com.inventory.manager.domain.item.ItemStockService;
import com.inventory.manager.exception.ConflictException;
import com.inventory.manager.exception.CustomExceptionCodes;

@Service
public class SalesInvoiceService {

    @Autowired
    private SalesInvoiceRepository salesInvoiceRepo;

    @Autowired
    private SalesInvoiceLineRepository salesInvoiceLineRepo;

    @Autowired
    private ItemStockService itemStockService;

    public Integer createSalesInvoice(SalesInvoice salesInvoice, List<SalesInvoiceLine> salesInvoiceLines) {

        salesInvoice.setIsDeleted(false);
        salesInvoiceRepo.save(salesInvoice);

        for (SalesInvoiceLine sil : salesInvoiceLines) {

            sil.setSalesInvoice(salesInvoice);
            sil.setIsDeleted(false);
            salesInvoiceLineRepo.save(sil);

            // Update the actual stock
            ItemStock itemStock = itemStockService.findItemStock(sil.getItem(), salesInvoice.getLocation());
            if (itemStock == null || itemStock.getQuantity() < sil.getQuantity()) {
                throw new ConflictException(CustomExceptionCodes.INSUFFICIENT_QUANTITY.toString(), "Item '"
                        + sil.getItem().getName() + "' has insufficient stock available for a sale.");
            } else {
                itemStock.setQuantity(itemStock.getQuantity() - sil.getQuantity());
                itemStockService.updateItemStock(itemStock);
            }
        }

        return salesInvoice.getId();
    }

    public Page<SalesInvoice> findAll(String keyword, Integer page, Integer count, String sortDirection, String sortBy) {

        Pageable pageable = new PageRequest(page, count, Direction.fromString(sortDirection), sortBy);
        Page<SalesInvoice> sis = salesInvoiceRepo.findAll(keyword, pageable);
        return sis;
    }

    public Page<SalesInvoice> findAllByCustomer(Integer customerId, String keyword, Integer page, Integer count,
            String sortDirection, String sortBy) {

        Pageable pageable = new PageRequest(page, count, Direction.fromString(sortDirection), sortBy);
        Page<SalesInvoice> sis = salesInvoiceRepo.findAllByCusomer(customerId, keyword, pageable);
        return sis;
    }

    public SalesInvoice findById(Integer id) {

        return salesInvoiceRepo.findByIdAndIsDeleted(id, false);
    }

    public void delete(SalesInvoice salesInvoice) {

        List<SalesInvoiceLine> sils = salesInvoice.getSalesInvoiceLines().stream().filter(sil -> !sil.getIsDeleted())
                .collect(Collectors.toList());

        for (SalesInvoiceLine sil : sils) {

            if (sil.getItem().getIsDeleted()) {
                throw new ConflictException(CustomExceptionCodes.ITEM_ALREADY_DELETED.toString(), "Item '"
                        + sil.getItem().getName()
                        + "' has been already deleted, therefore cannot undo the sale transaction.");
            }
            ItemStock itemStock = itemStockService.findItemStock(sil.getItem(), salesInvoice.getLocation());
            itemStock.setQuantity(itemStock.getQuantity() + sil.getQuantity());
            itemStockService.updateItemStock(itemStock);
        }

        sils.forEach(sil -> sil.setIsDeleted(true));
        salesInvoiceLineRepo.save(sils);

        salesInvoice.setIsDeleted(true);
        salesInvoiceRepo.save(salesInvoice);

    }
}
