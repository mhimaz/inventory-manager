package com.inventory.manager.application.item;

import java.math.BigDecimal;
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
import com.inventory.manager.application.item.price.PriceRequestDTO;
import com.inventory.manager.application.item.price.PriceResponseDTO;
import com.inventory.manager.application.location.LocationTransformer;
import com.inventory.manager.application.shared.dto.LocationQuantityDTO;
import com.inventory.manager.application.shared.dto.LocationQuantityResponseDTO;
import com.inventory.manager.application.shared.dto.PaginationDTO;
import com.inventory.manager.application.supplier.SupplierTransformer;
//import com.inventory.manager.domain.enums.Location;
import com.inventory.manager.domain.enums.PriceType;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.item.ItemStock;
import com.inventory.manager.domain.item.price.ItemPrice;
import com.inventory.manager.domain.location.Location;
import com.inventory.manager.domain.supplier.Supplier;

@Component
public class ItemTransformer {

    @Autowired
    private SupplierTransformer supplierTransformer;

    @Autowired
    private LocationTransformer locationTransformer;

    public Item toItem(Supplier supplier, CreateItemRequestDTO requestDTO) {
        Item item = new Item();
        item.setSupplier(supplier);
        item.setName(requestDTO.getName());
        item.setCode(requestDTO.getCode());
        return item;
    }

    public ItemPrice toItemPrice(PriceRequestDTO requestDTO) {
        ItemPrice itemPrice = new ItemPrice();
        itemPrice.setBuyingPrice(BigDecimal.valueOf(requestDTO.getBuyingPrice()));
        itemPrice.setSellingPrice(BigDecimal.valueOf(requestDTO.getSellingPrice()));
        itemPrice.setPriceType(PriceType.valueOf(requestDTO.getPriceType().toUpperCase(Locale.ENGLISH)));
        return itemPrice;
    }

    public Item toItem(UpdateItemRequestDTO requestDTO, Item item) {
        item.setName(requestDTO.getName());
        item.setCode(requestDTO.getCode());

        return item;
    }

    public ItemPrice toItemPrice(PriceRequestDTO requestDTO, ItemPrice price) {
        price.setBuyingPrice(BigDecimal.valueOf(requestDTO.getBuyingPrice()));
        price.setSellingPrice(BigDecimal.valueOf(requestDTO.getSellingPrice()));
        price.setPriceType(PriceType.valueOf(requestDTO.getPriceType().toUpperCase(Locale.ENGLISH)));

        return price;
    }

    public List<ItemStock> toItemStocks(List<LocationQuantityDTO> locationQuantityDTOs, List<Location> locations) {
        List<ItemStock> itemStocks = new ArrayList<ItemStock>();

        for (LocationQuantityDTO locationQuantityDTO : locationQuantityDTOs) {
            ItemStock itemStock = new ItemStock();

            Location location = locations.stream().filter(l -> l.getId().equals(locationQuantityDTO.getLocationId()))
                    .findFirst().get();
            itemStock.setLocation(location);
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
        boolean hasNegativeStock = false;
        if (item.getItemStocks() != null) {
            for (ItemStock itemStock : item.getItemStocks()) {
                responseDTO.getLocationQuantities().add(this.toLocationQuantityResponseDTO(itemStock));
                totalQuantity += itemStock.getQuantity();
                if (itemStock.getQuantity() < 0) {
                    hasNegativeStock = true;
                }
            }
        }
        responseDTO.setTotalQuantity(totalQuantity);
        responseDTO.setHasNegativeStock(hasNegativeStock);
        responseDTO.setPrice(this.toPriceResponseDTO(item.getPrice()));

        return responseDTO;
    }

    public PriceResponseDTO toPriceResponseDTO(ItemPrice itemPrice) {
        PriceResponseDTO responseDTO = new PriceResponseDTO();
        if (itemPrice != null) {
            responseDTO.setId(itemPrice.getId());
            responseDTO.setBuyingPrice(itemPrice.getBuyingPrice().doubleValue());
            responseDTO.setSellingPrice(itemPrice.getSellingPrice().doubleValue());
            responseDTO.setPriceType(itemPrice.getPriceType().name());
        }

        return responseDTO;

    }

    public LocationQuantityResponseDTO toLocationQuantityResponseDTO(ItemStock itemStock) {
        LocationQuantityResponseDTO locationQuantityDTO = new LocationQuantityResponseDTO();
        locationQuantityDTO.setLocation(locationTransformer.toGetLocationResponseDTO(itemStock.getLocation()));
        locationQuantityDTO.setQuantity(itemStock.getQuantity());
        return locationQuantityDTO;

    }

}
