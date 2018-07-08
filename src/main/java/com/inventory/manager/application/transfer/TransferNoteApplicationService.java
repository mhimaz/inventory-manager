package com.inventory.manager.application.transfer;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.manager.application.transfer.dto.CreateTransferNoteRequestDTO;
import com.inventory.manager.application.transfer.dto.GetTransferNoteResponseDTO;
import com.inventory.manager.application.transfer.dto.ListTransferNoteResponseDTO;
import com.inventory.manager.application.transfer.dto.TransferNoteLineRequestDTO;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.item.ItemService;
import com.inventory.manager.domain.location.Location;
import com.inventory.manager.domain.location.LocationService;
import com.inventory.manager.domain.stockhistory.transfer.TransferNote;
import com.inventory.manager.domain.stockhistory.transfer.TransferNoteLine;
import com.inventory.manager.domain.stockhistory.transfer.TransferNoteService;
import com.inventory.manager.exception.NotFoundException;

@Service
public class TransferNoteApplicationService {

    private static final Logger logger = Logger.getLogger(TransferNoteApplicationService.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private TransferNoteTransformer transferNoteTransformer;

    @Autowired
    private TransferNoteService transferNoteService;

    @Autowired
    private LocationService locationService;

    @Transactional
    public Integer createTransferNote(CreateTransferNoteRequestDTO requestDTO) {
        logger.info("[ Create Transfer Note :: " + requestDTO + " ]");

        List<Integer> itemIds = requestDTO.getTransferNoteLines().stream().map(TransferNoteLineRequestDTO::getItemId)
                .collect(Collectors.toList());
        List<Item> items = itemService.findByItemIds(itemIds);

        if (items.size() != itemIds.size()) {
            throw new NotFoundException("Item not found for one of the item ids: " + itemIds);
        }

        Location fromLocation = locationService.findLocationById(requestDTO.getFromLocationId());

        Location toLocation = locationService.findLocationById(requestDTO.getToLocationId());

        if (fromLocation == null) {
            throw new NotFoundException("From location not found for the the id: " + requestDTO.getFromLocationId());
        }
        if (toLocation == null) {
            throw new NotFoundException("To location not found for the the id: " + requestDTO.getToLocationId());
        }

        TransferNote transferNote = transferNoteTransformer.toTransferNote(requestDTO, fromLocation, toLocation);

        List<TransferNoteLine> transferNoteLines = transferNoteTransformer.toTransferNoteLines(
                requestDTO.getTransferNoteLines(), items);

        return transferNoteService.createTransferNote(transferNote, transferNoteLines);
    }

    @Transactional
    public ListTransferNoteResponseDTO listTransferNotes(String keyword, int page, int count, String sortDirection,
            String sortBy) {

        Page<TransferNote> tns = transferNoteService.findAll(keyword, page, count, sortDirection, sortBy);

        return transferNoteTransformer.toListTransferNoteResponseDTO(tns);
    }

    @Transactional
    public GetTransferNoteResponseDTO getTransferNote(Integer id) {

        TransferNote tn = transferNoteService.findById(id);
        if (tn == null) {
            throw new NotFoundException("Transfer note not found for the id: " + id);
        }
        return transferNoteTransformer.toGetTransferNoteResponseDTO(tn);
    }

    @Transactional
    public void deleteTransferNote(Integer id) {

        TransferNote tn = transferNoteService.findById(id);
        if (tn == null) {
            throw new NotFoundException("Transfer note not found for the id: " + id);
        }

        transferNoteService.delete(tn);
    }

}
