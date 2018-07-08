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
import com.inventory.manager.application.shared.dto.CalculateTotalRequestDTO;
import com.inventory.manager.application.shared.dto.CalculateTotalResponseDTO;
import com.inventory.manager.domain.enums.InvoiceType;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.item.ItemService;
import com.inventory.manager.domain.location.Location;
import com.inventory.manager.domain.location.LocationService;
import com.inventory.manager.domain.stockhistory.grn.GoodReceiveNote;
import com.inventory.manager.domain.stockhistory.grn.GoodReceiveNoteLine;
import com.inventory.manager.domain.stockhistory.grn.GoodReceiveNoteService;
import com.inventory.manager.domain.supplier.Supplier;
import com.inventory.manager.domain.supplier.SupplierService;
import com.inventory.manager.domain.util.TotalCalculator;
import com.inventory.manager.exception.CustomExceptionCodes;
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

    @Autowired
    private GRNSpecification grnSpec;

    @Autowired
    private TotalCalculator totalCalculator;

    @Autowired
    private LocationService locationService;

    @Transactional
    public Integer createGRN(CreateGoodReceiveNoteRequestDTO requestDTO) {
        logger.info("[ Create GRN :: " + requestDTO + " ]");

        Supplier supplier = supplierService.findSupplierById(requestDTO.getSupplierId());

        if (supplier == null) {
            throw new NotFoundException(CustomExceptionCodes.SUPPLIER_NOT_FOUND.toString(),
                    "Supplier not found for the id: " + requestDTO.getSupplierId());
        }
        List<Integer> itemIds = requestDTO.getGoodReceiveNoteLines().stream()
                .map(GoodReceiveNoteLineRequestDTO::getItemId).collect(Collectors.toList());
        List<Item> items = itemService.findBySupplierAndItemIds(supplier, itemIds);

        Location location = locationService.findLocationById(requestDTO.getLocationId());

        grnSpec.isSatisfiedBy(requestDTO, items, itemIds, location);

        List<GoodReceiveNoteLine> goodReceiveNoteLines = grnTransformer.toGoodReceiveNoteLines(
                requestDTO.getGoodReceiveNoteLines(), items);
        goodReceiveNoteLines = totalCalculator.updateGRNLineTotal(goodReceiveNoteLines);

        GoodReceiveNote goodReceiveNote = grnTransformer.toGoodReceiveNote(requestDTO, supplier, location);
        goodReceiveNote = totalCalculator.updateGRNTotal(goodReceiveNoteLines, goodReceiveNote);

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

        if (grn.getLocation().getIsDeleted()) {
            throw new NotFoundException("Location : '" + grn.getLocation()
                    + "' was deleted, therefore cannot delete the GRN");
        }

        goodReceiveNoteService.delete(grn);
    }

    public CalculateTotalResponseDTO calculateTotal(CalculateTotalRequestDTO requestDTO) {

        return totalCalculator.calculateTotal(requestDTO, InvoiceType.GRN);

    }
}
