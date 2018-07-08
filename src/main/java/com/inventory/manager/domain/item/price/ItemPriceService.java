package com.inventory.manager.domain.item.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemPriceService {

    @Autowired
    private ItemPriceRepository itemPriceRepo;

    public void updateItemPrice(ItemPrice itemPrice) {

        itemPriceRepo.save(itemPrice);
    }

}
