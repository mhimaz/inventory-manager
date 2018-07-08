package com.inventory.manager.application.adjustment;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.manager.application.adjustment.dto.CreateStockAdjustmentRequestDTO;
import com.inventory.manager.application.adjustment.dto.GetStockAdjustmentResponseDTO;
import com.inventory.manager.application.adjustment.dto.ListStockAdjustmentResponseDTO;
import com.inventory.manager.application.adjustment.dto.StockAdjustmentLineRequestDTO;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.item.ItemService;
import com.inventory.manager.domain.location.Location;
import com.inventory.manager.domain.location.LocationService;
import com.inventory.manager.domain.stockhistory.adjustment.StockAdjustment;
import com.inventory.manager.domain.stockhistory.adjustment.StockAdjustmentLine;
import com.inventory.manager.domain.stockhistory.adjustment.StockAdjustmentService;
import com.inventory.manager.exception.NotFoundException;

@Service
public class StockAdjustmentApplicationService {

    private static final Logger logger = Logger.getLogger(StockAdjustmentApplicationService.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private StockAdjustmentTransformer stockAdjustmentTransformer;

    @Autowired
    private StockAdjustmentService stockAdjustmentService;

    @Autowired
    private LocationService locationService;

    @Transactional
    public Integer createStockAdjustment(CreateStockAdjustmentRequestDTO requestDTO) {
        logger.info("[ Create Stock Adjustment :: " + requestDTO + " ]");

        List<Integer> itemIds = requestDTO.getStockAdjustmentLines().stream()
                .map(StockAdjustmentLineRequestDTO::getItemId).collect(Collectors.toList());
        List<Item> items = itemService.findByItemIds(itemIds);

        if (items.size() != itemIds.size()) {
            throw new NotFoundException("Item not found for one of the item ids: " + itemIds);
        }

        Location location = locationService.findLocationById(requestDTO.getLocationId());

        if (location == null) {
            throw new NotFoundException("Location not found for the id: " + requestDTO.getLocationId());
        }

        StockAdjustment stockAdjustment = stockAdjustmentTransformer.toStockAdjustment(requestDTO, location);

        List<StockAdjustmentLine> stockAdjustmentLines = stockAdjustmentTransformer.toStockAdjustmentLines(
                requestDTO.getStockAdjustmentLines(), items);

        return stockAdjustmentService.createStockAdjustment(stockAdjustment, stockAdjustmentLines);
    }

    @Transactional
    public ListStockAdjustmentResponseDTO listStockAdjustments(String keyword, int page, int count,
            String sortDirection, String sortBy) {

        Page<StockAdjustment> sas = stockAdjustmentService.findAll(keyword, page, count, sortDirection, sortBy);

        return stockAdjustmentTransformer.toListStockAdjustmentResponseDTO(sas);
    }

    @Transactional
    public GetStockAdjustmentResponseDTO getStockAdjustment(Integer id) {

        StockAdjustment sa = stockAdjustmentService.findById(id);
        if (sa == null) {
            throw new NotFoundException("Stock adjustment not found for the id: " + id);
        }
        return stockAdjustmentTransformer.toGetStockAdjustmentResponseDTO(sa);
    }
}
