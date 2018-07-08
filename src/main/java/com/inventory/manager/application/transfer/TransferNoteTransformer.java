package com.inventory.manager.application.transfer;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.inventory.manager.application.item.ItemTransformer;
import com.inventory.manager.application.location.LocationTransformer;
import com.inventory.manager.application.shared.dto.PaginationDTO;
import com.inventory.manager.application.transfer.dto.CreateTransferNoteRequestDTO;
import com.inventory.manager.application.transfer.dto.GetTransferNoteResponseDTO;
import com.inventory.manager.application.transfer.dto.ListTransferNoteResponseDTO;
import com.inventory.manager.application.transfer.dto.TransferNoteLineRequestDTO;
import com.inventory.manager.application.transfer.dto.TransferNoteLineResponseDTO;
//import com.inventory.manager.domain.enums.Location;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.location.Location;
import com.inventory.manager.domain.stockhistory.transfer.TransferNote;
import com.inventory.manager.domain.stockhistory.transfer.TransferNoteLine;

@Component
public class TransferNoteTransformer {

    @Autowired
    private ItemTransformer itemTransformer;

    @Autowired
    private LocationTransformer locationTransformer;

    public TransferNote toTransferNote(CreateTransferNoteRequestDTO requestDTO, Location fromLocation,
            Location toLocation) {
        TransferNote transferNote = new TransferNote();
        transferNote.setTransferredDate(new DateTime(requestDTO.getTransferredDate()));
        transferNote.setFromLocation(fromLocation);
        transferNote.setToLocation(toLocation);
        transferNote.setRemarks(requestDTO.getRemarks());
        return transferNote;
    }

    public List<TransferNoteLine> toTransferNoteLines(List<TransferNoteLineRequestDTO> requestDTOs, List<Item> items) {
        List<TransferNoteLine> transferNoteLines = new ArrayList<TransferNoteLine>();

        for (TransferNoteLineRequestDTO tnl : requestDTOs) {
            TransferNoteLine noteLine = new TransferNoteLine();
            Item item = items.stream().filter(i -> i.getId().equals(tnl.getItemId())).findFirst().get();
            noteLine.setItem(item);
            noteLine.setQuantity(tnl.getQuantity());
            transferNoteLines.add(noteLine);
        }
        return transferNoteLines;
    }

    public ListTransferNoteResponseDTO toListTransferNoteResponseDTO(Page<TransferNote> tns) {
        ListTransferNoteResponseDTO responseDTO = new ListTransferNoteResponseDTO();

        PaginationDTO paginationDetails = PaginationDTO.getInstance(tns.getTotalElements(), tns.getSize(),
                tns.getTotalPages(), tns.getNumber());
        responseDTO.setPagination(paginationDetails);

        for (TransferNote tn : tns.getContent()) {
            responseDTO.getTransferNotes().add(this.toLightGetTransferNoteResponseDTO(tn));
        }

        return responseDTO;
    }

    public GetTransferNoteResponseDTO toLightGetTransferNoteResponseDTO(TransferNote transferNote) {
        GetTransferNoteResponseDTO responseDTO = new GetTransferNoteResponseDTO();

        responseDTO.setId(transferNote.getId());
        responseDTO.setFromLocation(locationTransformer.toGetLocationResponseDTO(transferNote.getFromLocation()));
        responseDTO.setToLocation(locationTransformer.toGetLocationResponseDTO(transferNote.getToLocation()));
        responseDTO.setTransferredDate(transferNote.getTransferredDate().toDate());
        responseDTO.setRemarks(transferNote.getRemarks());
        return responseDTO;
    }

    public GetTransferNoteResponseDTO toGetTransferNoteResponseDTO(TransferNote transferNote) {
        GetTransferNoteResponseDTO responseDTO = toLightGetTransferNoteResponseDTO(transferNote);

        for (TransferNoteLine sil : transferNote.getTransferNoteLines()) {
            responseDTO.getLines().add(this.toTransferNoteLineResponseDTO(sil));
        }
        return responseDTO;
    }

    public TransferNoteLineResponseDTO toTransferNoteLineResponseDTO(TransferNoteLine transferNoteLine) {
        TransferNoteLineResponseDTO responseDTO = new TransferNoteLineResponseDTO();

        responseDTO.setId(transferNoteLine.getId());
        responseDTO.setItem(itemTransformer.toGetItemResponseDTO(transferNoteLine.getItem()));
        responseDTO.setQuantity(transferNoteLine.getQuantity());
        return responseDTO;
    }

}
