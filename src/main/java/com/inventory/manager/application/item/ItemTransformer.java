package com.inventory.manager.application.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.inventory.manager.application.item.dto.CreateItemRequestDTO;
import com.inventory.manager.application.item.dto.GetItemResponseDTO;
import com.inventory.manager.application.item.dto.ListItemsResponseDTO;
import com.inventory.manager.application.item.dto.UpdateItemRequestDTO;
import com.inventory.manager.application.shared.dto.LocationQuantityDTO;
import com.inventory.manager.application.shared.dto.PaginationDTO;
import com.inventory.manager.application.supplier.SupplierTransformer;
import com.inventory.manager.domain.enums.Location;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.item.ItemStock;
import com.inventory.manager.domain.supplier.Supplier;

@Component
public class ItemTransformer {

    @Autowired
    private SupplierTransformer supplierTransformer;

    public Item toItem(Supplier supplier, CreateItemRequestDTO requestDTO) {
        Item item = new Item();
        item.setSupplier(supplier);
        item.setName(requestDTO.getName());
        item.setCode(requestDTO.getCode());
        return item;
    }

    public Item toItem(UpdateItemRequestDTO requestDTO, Item item) {
        item.setName(requestDTO.getName());
        item.setCode(requestDTO.getCode());
        return item;
    }

    public List<ItemStock> toItemStocks(List<LocationQuantityDTO> locationQuantityDTOs) {
        List<ItemStock> itemStocks = new ArrayList<ItemStock>();

        for (LocationQuantityDTO locationQuantityDTO : locationQuantityDTOs) {
            ItemStock itemStock = new ItemStock();
            itemStock.setLocation(Location.valueOf(locationQuantityDTO.getLocation().toUpperCase(Locale.ENGLISH)));
            itemStock.setQuantity(locationQuantityDTO.getQuantity());
            itemStocks.add(itemStock);
        }
        return itemStocks;
    }

    public ListItemsResponseDTO toListItemsResponseDTO(Page<Item> items) {
        ListItemsResponseDTO responseDTO = new ListItemsResponseDTO();

        PaginationDTO paginationDetails = PaginationDTO.getInstance(items.getTotalElements(), items.getSize(),
                items.getTotalPages(), items.getNumber());
        responseDTO.setPagination(paginationDetails);

        for (Item item : items.getContent()) {
            responseDTO.getItems().add(this.toGetItemResponseDTO(item));
        }

        return responseDTO;
    }

    public ListItemsResponseDTO toListItemsResponseDTO(List<Item> items) {
        ListItemsResponseDTO responseDTO = new ListItemsResponseDTO();

        for (Item item : items) {
            responseDTO.getItems().add(this.toGetItemResponseDTO(item));
        }

        return responseDTO;
    }

    public GetItemResponseDTO toGetItemResponseDTO(Item item) {
        GetItemResponseDTO responseDTO = new GetItemResponseDTO();
        responseDTO.setId(item.getId());
        responseDTO.setName(item.getName());
        responseDTO.setCode(item.getCode());
        responseDTO.setSupplier(supplierTransformer.toGetSupplierResponseDTO(item.getSupplier()));

        Long totalQuantity = 0L;
        if (item.getItemStocks() != null) {
            for (ItemStock itemStock : item.getItemStocks()) {
                responseDTO.getLocationQuantities().add(this.toLocationQuantityDTO(itemStock));
                totalQuantity += itemStock.getQuantity();
            }
        }
        responseDTO.setTotalQuantity(totalQuantity);

        return responseDTO;
    }

    public LocationQuantityDTO toLocationQuantityDTO(ItemStock itemStock) {
        LocationQuantityDTO locationQuantityDTO = new LocationQuantityDTO();
        locationQuantityDTO.setLocation(itemStock.getLocation().getLabel());
        locationQuantityDTO.setQuantity(itemStock.getQuantity());
        return locationQuantityDTO;

    }

}
