package com.inventory.manager.application.adjustment;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.inventory.manager.application.adjustment.dto.CreateStockAdjustmentRequestDTO;
import com.inventory.manager.application.adjustment.dto.GetStockAdjustmentResponseDTO;
import com.inventory.manager.application.adjustment.dto.ListStockAdjustmentResponseDTO;
import com.inventory.manager.application.adjustment.dto.StockAdjustmentLineRequestDTO;
import com.inventory.manager.application.adjustment.dto.StockAdjustmentLineResponseDTO;
import com.inventory.manager.application.item.ItemTransformer;
import com.inventory.manager.application.location.LocationTransformer;
import com.inventory.manager.application.shared.dto.PaginationDTO;
//import com.inventory.manager.domain.enums.Location;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.location.Location;
import com.inventory.manager.domain.stockhistory.adjustment.StockAdjustment;
import com.inventory.manager.domain.stockhistory.adjustment.StockAdjustmentLine;

@Component
public class StockAdjustmentTransformer {

    @Autowired
    private ItemTransformer itemTransformer;
    
    @Autowired
    private LocationTransformer locationTransformer;

    public StockAdjustment toStockAdjustment(CreateStockAdjustmentRequestDTO requestDTO, Location location) {
        StockAdjustment stockAdjustment = new StockAdjustment();
        stockAdjustment.setAdjustedDate(DateTime.now());
        stockAdjustment.setLocation(location);
        stockAdjustment.setRemarks(requestDTO.getRemarks());
        return stockAdjustment;
    }

    public List<StockAdjustmentLine> toStockAdjustmentLines(List<StockAdjustmentLineRequestDTO> requestDTOs,
            List<Item> items) {
        List<StockAdjustmentLine> stockAdjustmentLines = new ArrayList<>();

        for (StockAdjustmentLineRequestDTO sal : requestDTOs) {
            StockAdjustmentLine adjLine = new StockAdjustmentLine();
            Item item = items.stream().filter(i -> i.getId().equals(sal.getItemId())).findFirst().get();
            adjLine.setItem(item);
            adjLine.setQuantity(sal.getQuantity());
            stockAdjustmentLines.add(adjLine);
        }
        return stockAdjustmentLines;
    }

    public ListStockAdjustmentResponseDTO toListStockAdjustmentResponseDTO(Page<StockAdjustment> sas) {
        ListStockAdjustmentResponseDTO responseDTO = new ListStockAdjustmentResponseDTO();

        PaginationDTO paginationDetails = PaginationDTO.getInstance(sas.getTotalElements(), sas.getSize(),
                sas.getTotalPages(), sas.getNumber());
        responseDTO.setPagination(paginationDetails);

        for (StockAdjustment sa : sas.getContent()) {
            responseDTO.getStockAdjustments().add(this.toLightGetStockAdjustmentResponseDTO(sa));
        }

        return responseDTO;
    }

    public GetStockAdjustmentResponseDTO toLightGetStockAdjustmentResponseDTO(StockAdjustment stockAdjustment) {
        GetStockAdjustmentResponseDTO responseDTO = new GetStockAdjustmentResponseDTO();

        responseDTO.setId(stockAdjustment.getId());
        responseDTO.setLocation(locationTransformer.toGetLocationResponseDTO(stockAdjustment.getLocation()));
        responseDTO.setAdjustedDate(stockAdjustment.getAdjustedDate().toDate());
        responseDTO.setRemarks(stockAdjustment.getRemarks());
        return responseDTO;
    }

    public GetStockAdjustmentResponseDTO toGetStockAdjustmentResponseDTO(StockAdjustment stockAdjustment) {
        GetStockAdjustmentResponseDTO responseDTO = toLightGetStockAdjustmentResponseDTO(stockAdjustment);

        for (StockAdjustmentLine sal : stockAdjustment.getStockAdjustmentLines()) {
            responseDTO.getLines().add(this.toStockAdjustmentLineResponseDTO(sal));
        }
        return responseDTO;
    }

    public StockAdjustmentLineResponseDTO toStockAdjustmentLineResponseDTO(StockAdjustmentLine stockAdjustmentLine) {
        StockAdjustmentLineResponseDTO responseDTO = new StockAdjustmentLineResponseDTO();

        responseDTO.setId(stockAdjustmentLine.getId());
        responseDTO.setItem(itemTransformer.toGetItemResponseDTO(stockAdjustmentLine.getItem()));
        responseDTO.setQuantity(stockAdjustmentLine.getQuantity());
        return responseDTO;
    }

}
