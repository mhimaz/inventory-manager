package com.inventory.manager.application.grn;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.inventory.manager.application.grn.dto.CreateGoodReceiveNoteRequestDTO;
import com.inventory.manager.application.grn.dto.GetGoodReceiveNoteResponseDTO;
import com.inventory.manager.application.grn.dto.GoodReceiveNoteLineRequestDTO;
import com.inventory.manager.application.grn.dto.GoodReceiveNoteLineResponseDTO;
import com.inventory.manager.application.grn.dto.ListGoodReceiveNoteResponseDTO;
import com.inventory.manager.application.item.ItemTransformer;
import com.inventory.manager.application.shared.dto.PaginationDTO;
import com.inventory.manager.application.supplier.SupplierTransformer;
import com.inventory.manager.domain.enums.Location;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.stockhistory.grn.GoodReceiveNote;
import com.inventory.manager.domain.stockhistory.grn.GoodReceiveNoteLine;
//import com.inventory.manager.domain.stockhistory.PurchaseHistory;
import com.inventory.manager.domain.supplier.Supplier;

@Component
public class GRNTransformer {

    @Autowired
    private SupplierTransformer supplierTransformer;

    @Autowired
    private ItemTransformer itemTransformer;

    public GoodReceiveNote toGoodReceiveNote(CreateGoodReceiveNoteRequestDTO requestDTO, Supplier supplier) {
        GoodReceiveNote goodReceiveNote = new GoodReceiveNote();
        goodReceiveNote.setSupplier(supplier);
        goodReceiveNote.setPurchasedDate(new DateTime(requestDTO.getPurchasedDate()));
        goodReceiveNote.setReceiptNo(requestDTO.getReceiptNo());
        goodReceiveNote.setLocation(Location.valueOf(requestDTO.getLocation().toUpperCase(Locale.ENGLISH)));
        goodReceiveNote.setRemarks(requestDTO.getRemarks());
        return goodReceiveNote;
    }

    public List<GoodReceiveNoteLine> toGoodReceiveNoteLines(List<GoodReceiveNoteLineRequestDTO> requestDTOs,
            List<Item> items) {
        List<GoodReceiveNoteLine> goodReceiveNoteLines = new ArrayList<GoodReceiveNoteLine>();

        for (GoodReceiveNoteLineRequestDTO grnl : requestDTOs) {
            GoodReceiveNoteLine noteLine = new GoodReceiveNoteLine();
            Item item = items.stream().filter(i -> i.getId().equals(grnl.getItemId())).findFirst().get();
            noteLine.setItem(item);
            noteLine.setQuantity(grnl.getQuantity());
            goodReceiveNoteLines.add(noteLine);
        }
        return goodReceiveNoteLines;
    }

    public ListGoodReceiveNoteResponseDTO toListGoodReceiveNoteResponseDTO(Page<GoodReceiveNote> grns) {
        ListGoodReceiveNoteResponseDTO responseDTO = new ListGoodReceiveNoteResponseDTO();

        PaginationDTO paginationDetails = PaginationDTO.getInstance(grns.getTotalElements(), grns.getSize(),
                grns.getTotalPages(), grns.getNumber());
        responseDTO.setPagination(paginationDetails);

        for (GoodReceiveNote grn : grns.getContent()) {
            responseDTO.getGrns().add(this.toLightGetGoodReceiveNoteResponseDTO(grn));
        }

        return responseDTO;
    }

    public GetGoodReceiveNoteResponseDTO toLightGetGoodReceiveNoteResponseDTO(GoodReceiveNote goodReceiveNote) {
        GetGoodReceiveNoteResponseDTO responseDTO = new GetGoodReceiveNoteResponseDTO();

        responseDTO.setId(goodReceiveNote.getId());
        responseDTO.setSupplier(supplierTransformer.toGetSupplierResponseDTO(goodReceiveNote.getSupplier()));
        responseDTO.setReceiptNo(goodReceiveNote.getReceiptNo());
        responseDTO.setLocation(goodReceiveNote.getLocation().getLabel());
        responseDTO.setPurchasedDate(goodReceiveNote.getPurchasedDate().toDate());
        responseDTO.setRemarks(goodReceiveNote.getRemarks());
        return responseDTO;
    }

    public GetGoodReceiveNoteResponseDTO toGetGoodReceiveNoteResponseDTO(GoodReceiveNote goodReceiveNote) {
        GetGoodReceiveNoteResponseDTO responseDTO = toLightGetGoodReceiveNoteResponseDTO(goodReceiveNote);

        for (GoodReceiveNoteLine grnl : goodReceiveNote.getGoodReceiveNoteLines()) {
            responseDTO.getLines().add(this.toGoodReceiveNoteLineResponseDTO(grnl));
        }
        return responseDTO;
    }

    public GoodReceiveNoteLineResponseDTO toGoodReceiveNoteLineResponseDTO(GoodReceiveNoteLine goodReceiveNoteLine) {
        GoodReceiveNoteLineResponseDTO responseDTO = new GoodReceiveNoteLineResponseDTO();

        responseDTO.setId(goodReceiveNoteLine.getId());
        responseDTO.setItem(itemTransformer.toGetItemResponseDTO(goodReceiveNoteLine.getItem()));
        responseDTO.setQuantity(goodReceiveNoteLine.getQuantity());
        return responseDTO;
    }

}
