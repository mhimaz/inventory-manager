package com.inventory.manager.application.grn;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.manager.application.grn.dto.CreateGoodReceiveNoteRequestDTO;
import com.inventory.manager.application.grn.dto.GetGoodReceiveNoteResponseDTO;
import com.inventory.manager.application.grn.dto.GoodReceiveNoteLineRequestDTO;
import com.inventory.manager.application.grn.dto.ListGoodReceiveNoteResponseDTO;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.item.ItemService;
import com.inventory.manager.domain.stockhistory.grn.GoodReceiveNote;
import com.inventory.manager.domain.stockhistory.grn.GoodReceiveNoteLine;
import com.inventory.manager.domain.stockhistory.grn.GoodReceiveNoteService;
import com.inventory.manager.domain.supplier.Supplier;
import com.inventory.manager.domain.supplier.SupplierService;
import com.inventory.manager.exception.NotFoundException;

@Service
public class GRNApplicationService {

    private static final Logger logger = Logger.getLogger(GRNApplicationService.class);

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private GRNTransformer grnTransformer;

    @Autowired
    private GoodReceiveNoteService goodReceiveNoteService;

    @Transactional
    public Integer createGRN(CreateGoodReceiveNoteRequestDTO requestDTO) {
        logger.info("[ Create GRN :: " + requestDTO + " ]");

        Supplier supplier = supplierService.findSupplierById(requestDTO.getSupplierId());
        List<Integer> itemIds = requestDTO.getGoodReceiveNoteLines().stream()
                .map(GoodReceiveNoteLineRequestDTO::getItemId).collect(Collectors.toList());
        List<Item> items = itemService.findBySupplierAndItemIds(supplier, itemIds);

        if (items.size() != itemIds.size()) {
            throw new NotFoundException("Item not found for one of the item ids: " + itemIds);
        }

        GoodReceiveNote goodReceiveNote = grnTransformer.toGoodReceiveNote(requestDTO, supplier);
        List<GoodReceiveNoteLine> goodReceiveNoteLines = grnTransformer.toGoodReceiveNoteLines(
                requestDTO.getGoodReceiveNoteLines(), items);

        return goodReceiveNoteService.createGoodReceiveNote(goodReceiveNote, goodReceiveNoteLines);
    }

    @Transactional
    public ListGoodReceiveNoteResponseDTO listGRNs(String keyword, int page, int count, String sortDirection,
            String sortBy) {

        Page<GoodReceiveNote> grns = goodReceiveNoteService.findAll(keyword, page, count, sortDirection, sortBy);

        return grnTransformer.toListGoodReceiveNoteResponseDTO(grns);
    }

    @Transactional
    public ListGoodReceiveNoteResponseDTO listGRNsBySupplier(Integer supplierId, String keyword, int page, int count,
            String sortDirection, String sortBy) {

        Page<GoodReceiveNote> grns = goodReceiveNoteService.findAllBySupplier(supplierId, keyword, page, count,
                sortDirection, sortBy);

        return grnTransformer.toListGoodReceiveNoteResponseDTO(grns);
    }

    @Transactional
    public GetGoodReceiveNoteResponseDTO getGRN(Integer id) {

        GoodReceiveNote grn = goodReceiveNoteService.findById(id);
        if (grn == null) {
            throw new NotFoundException("GRN not found for the id: " + id);
        }
        return grnTransformer.toGetGoodReceiveNoteResponseDTO(grn);
    }

    @Transactional
    public void deleteGRN(Integer id) {

        GoodReceiveNote grn = goodReceiveNoteService.findById(id);
        if (grn == null) {
            throw new NotFoundException("GRN not found for the id: " + id);
        }

        goodReceiveNoteService.delete(grn);
    }

}
