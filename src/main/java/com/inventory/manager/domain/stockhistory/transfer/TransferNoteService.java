package com.inventory.manager.domain.stockhistory.transfer;

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
public class TransferNoteService {

    @Autowired
    private TransferNoteRepository transferNoteRepo;

    @Autowired
    private TransferNoteLineRepository transferNoteLineRepo;

    @Autowired
    private ItemStockService itemStockService;

    public Integer createTransferNote(TransferNote transferNote, List<TransferNoteLine> transferNoteLines) {

        transferNote.setIsDeleted(false);
        transferNoteRepo.save(transferNote);

        for (TransferNoteLine tnl : transferNoteLines) {

            tnl.setTransferNote(transferNote);
            tnl.setIsDeleted(false);
            transferNoteLineRepo.save(tnl);

            // Deduct the from location stock
            ItemStock itemStockToDeduct = itemStockService.findItemStock(tnl.getItem(), transferNote.getFromLocation());
            if (itemStockToDeduct == null || itemStockToDeduct.getQuantity() < tnl.getQuantity()) {
                throw new ConflictException(CustomExceptionCodes.INSUFFICIENT_QUANTITY.toString(), "Item '"
                        + tnl.getItem().getName() + "' has insufficient stock available for a transfer.");
            } else {
                itemStockToDeduct.setQuantity(itemStockToDeduct.getQuantity() - tnl.getQuantity());
                itemStockService.updateItemStock(itemStockToDeduct);
            }

            // Add the to location stock
            ItemStock itemStockToAdd = itemStockService.findItemStock(tnl.getItem(), transferNote.getToLocation());
            if (itemStockToAdd == null) {
                itemStockToAdd = new ItemStock();
                itemStockToAdd.setItem(tnl.getItem());
                itemStockToAdd.setLocation(transferNote.getToLocation());
                itemStockToAdd.setQuantity(tnl.getQuantity());
                itemStockService.createItemStock(itemStockToAdd);
            } else {
                itemStockToAdd.setQuantity(itemStockToAdd.getQuantity() + tnl.getQuantity());
                itemStockService.updateItemStock(itemStockToAdd);
            }
        }

        return transferNote.getId();
    }

    public Page<TransferNote> findAll(String keyword, Integer page, Integer count, String sortDirection, String sortBy) {

        Pageable pageable = new PageRequest(page, count, Direction.fromString(sortDirection), sortBy);
        Page<TransferNote> tns = transferNoteRepo.findAll(pageable);
        return tns;
    }

    public TransferNote findById(Integer id) {

        return transferNoteRepo.findByIdAndIsDeleted(id, false);
    }

    public void delete(TransferNote transferNote) {

        List<TransferNoteLine> tnls = transferNote.getTransferNoteLines().stream().filter(tnl -> !tnl.getIsDeleted())
                .collect(Collectors.toList());

        for (TransferNoteLine tnl : tnls) {

            if (tnl.getItem().getIsDeleted()) {
                throw new ConflictException(CustomExceptionCodes.ITEM_ALREADY_DELETED.toString(), "Item '"
                        + tnl.getItem().getName()
                        + "' has been already deleted, therefore cannot undo transfer transaction");
            }

            ItemStock itemStockToDeduct = itemStockService.findItemStock(tnl.getItem(), transferNote.getToLocation());
            if (itemStockToDeduct.getQuantity() < tnl.getQuantity()) {
                throw new ConflictException(CustomExceptionCodes.INSUFFICIENT_QUANTITY.toString(), "Item '"
                        + tnl.getItem().getName() + "' has insufficient stock available to undo the transfer.");
            }
            itemStockToDeduct.setQuantity(itemStockToDeduct.getQuantity() - tnl.getQuantity());
            itemStockService.updateItemStock(itemStockToDeduct);

            ItemStock itemStockToAdd = itemStockService.findItemStock(tnl.getItem(), transferNote.getFromLocation());
            itemStockToAdd.setQuantity(itemStockToAdd.getQuantity() + tnl.getQuantity());
            itemStockService.updateItemStock(itemStockToAdd);
        }

        tnls.forEach(tnl -> tnl.setIsDeleted(true));
        transferNoteLineRepo.save(tnls);

        transferNote.setIsDeleted(true);
        transferNoteRepo.save(transferNote);

    }
}
