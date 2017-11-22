package com.inventory.manager.domain.stockhistory.grn;

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
public class GoodReceiveNoteService {

    @Autowired
    private GoodReceiveNoteRepository goodReceiveNoteRepo;

    @Autowired
    private GoodReceiveNoteLineRepository goodReceiveNoteLineRepo;

    @Autowired
    private ItemStockService itemStockService;

    public Integer createGoodReceiveNote(GoodReceiveNote goodReceiveNote, List<GoodReceiveNoteLine> goodReceiveNoteLines) {

        goodReceiveNote.setIsDeleted(false);
        goodReceiveNoteRepo.save(goodReceiveNote);

        for (GoodReceiveNoteLine grnl : goodReceiveNoteLines) {

            grnl.setGoodReceiveNote(goodReceiveNote);
            grnl.setIsDeleted(false);
            goodReceiveNoteLineRepo.save(grnl);

            // Update the actual stock
            ItemStock itemStock = itemStockService.findItemStock(grnl.getItem(), goodReceiveNote.getLocation());
            if (itemStock == null) {
                itemStock = new ItemStock();
                itemStock.setItem(grnl.getItem());
                itemStock.setLocation(goodReceiveNote.getLocation());
                itemStock.setQuantity(grnl.getQuantity());
                itemStockService.createItemStock(itemStock);
            } else {
                itemStock.setQuantity(itemStock.getQuantity() + grnl.getQuantity());
                itemStockService.updateItemStock(itemStock);
            }
        }

        return goodReceiveNote.getId();
    }

    public Page<GoodReceiveNote> findAll(String keyword, Integer page, Integer count, String sortDirection,
            String sortBy) {

        Pageable pageable = new PageRequest(page, count, Direction.fromString(sortDirection), sortBy);
        Page<GoodReceiveNote> grns = goodReceiveNoteRepo.findAll(keyword, pageable);
        return grns;
    }

    public Page<GoodReceiveNote> findAllBySupplier(Integer supplierId, String keyword, Integer page, Integer count,
            String sortDirection, String sortBy) {

        Pageable pageable = new PageRequest(page, count, Direction.fromString(sortDirection), sortBy);
        Page<GoodReceiveNote> grns = goodReceiveNoteRepo.findAllBySupplier(supplierId, keyword, pageable);
        return grns;
    }

    public GoodReceiveNote findById(Integer id) {

        return goodReceiveNoteRepo.findByIdAndIsDeleted(id, false);
    }

    public void delete(GoodReceiveNote goodReceiveNote) {

        List<GoodReceiveNoteLine> grnls = goodReceiveNote.getGoodReceiveNoteLines().stream()
                .filter(grnl -> !grnl.getIsDeleted()).collect(Collectors.toList());

        for (GoodReceiveNoteLine grnl : grnls) {

            if (grnl.getItem().getIsDeleted()) {
                throw new ConflictException(CustomExceptionCodes.ITEM_ALREADY_DELETED.toString(), "Item '"
                        + grnl.getItem().getName() + "' has been already deleted, therefore cannot undo the GRN.");
            }
            ItemStock itemStock = itemStockService.findItemStock(grnl.getItem(), goodReceiveNote.getLocation());

            if (itemStock.getQuantity() < grnl.getQuantity()) {
                throw new ConflictException(CustomExceptionCodes.INSUFFICIENT_QUANTITY.toString(), "Item '"
                        + grnl.getItem().getName() + "' has insufficient stock available to undo the GRN.");
            }
            itemStock.setQuantity(itemStock.getQuantity() - grnl.getQuantity());
            itemStockService.updateItemStock(itemStock);
        }

        grnls.forEach(grnl -> grnl.setIsDeleted(true));
        goodReceiveNoteLineRepo.save(grnls);

        goodReceiveNote.setIsDeleted(true);
        goodReceiveNoteRepo.save(goodReceiveNote);

    }
}
