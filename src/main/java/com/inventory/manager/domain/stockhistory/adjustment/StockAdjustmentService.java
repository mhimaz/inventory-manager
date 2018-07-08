package com.inventory.manager.domain.stockhistory.adjustment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.inventory.manager.domain.item.ItemStock;
import com.inventory.manager.domain.item.ItemStockService;

@Service
public class StockAdjustmentService {

    @Autowired
    private StockAdjustmentRepository stockAdjustmentRepo;

    @Autowired
    private StockAdjustmentLineRepository stockAdjustmentLineRepo;

    @Autowired
    private ItemStockService itemStockService;

    public Integer createStockAdjustment(StockAdjustment stockAdjustment, List<StockAdjustmentLine> stockAdjustmentLines) {

        stockAdjustment.setIsDeleted(false);
        stockAdjustmentRepo.save(stockAdjustment);

        for (StockAdjustmentLine sal : stockAdjustmentLines) {

            sal.setStockAdjustment(stockAdjustment);
            sal.setIsDeleted(false);
            stockAdjustmentLineRepo.save(sal);

            ItemStock itemStockToAdjust = itemStockService.findItemStock(sal.getItem(), stockAdjustment.getLocation());
            if (itemStockToAdjust == null) {
                itemStockToAdjust = new ItemStock();
                itemStockToAdjust.setItem(sal.getItem());
                itemStockToAdjust.setLocation(stockAdjustment.getLocation());
                itemStockToAdjust.setQuantity(sal.getQuantity());
                itemStockService.createItemStock(itemStockToAdjust);
            } else {
                itemStockToAdjust.setQuantity(itemStockToAdjust.getQuantity() + sal.getQuantity());
                itemStockService.updateItemStock(itemStockToAdjust);
            }
        }

        return stockAdjustment.getId();
    }

    public Page<StockAdjustment> findAll(String keyword, Integer page, Integer count, String sortDirection,
            String sortBy) {

        Pageable pageable = new PageRequest(page, count, Direction.fromString(sortDirection), sortBy);
        Page<StockAdjustment> tns = stockAdjustmentRepo.findAll(keyword, pageable);
        return tns;
    }

    public StockAdjustment findById(Integer id) {

        return stockAdjustmentRepo.findByIdAndIsDeleted(id, false);
    }

}
