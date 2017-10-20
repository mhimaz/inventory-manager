package com.inventory.manager.application.sales;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.inventory.manager.application.customer.CustomerTransformer;
import com.inventory.manager.application.item.ItemTransformer;
import com.inventory.manager.application.sales.dto.CreateSalesInvoiceRequestDTO;
import com.inventory.manager.application.sales.dto.GetSalesInvoiceResponseDTO;
import com.inventory.manager.application.sales.dto.ListSalesInvoiceResponseDTO;
import com.inventory.manager.application.sales.dto.SalesInvoiceLineRequestDTO;
import com.inventory.manager.application.sales.dto.SalesInvoiceLineResponseDTO;
import com.inventory.manager.application.shared.dto.PaginationDTO;
import com.inventory.manager.domain.customer.Customer;
import com.inventory.manager.domain.enums.Location;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.stockhistory.sales.SalesInvoice;
import com.inventory.manager.domain.stockhistory.sales.SalesInvoiceLine;

//import com.inventory.manager.domain.stockhistory.PurchaseHistory;

@Component
public class SalesInvoiceTransformer {

    @Autowired
    private CustomerTransformer customerTransformer;

    @Autowired
    private ItemTransformer itemTransformer;

    public SalesInvoice toSalesInvoice(CreateSalesInvoiceRequestDTO requestDTO, Customer customer) {
        SalesInvoice salesInvoice = new SalesInvoice();
        salesInvoice.setCustomer(customer);
        salesInvoice.setSoldDate(new DateTime(requestDTO.getSoldDate()));
        salesInvoice.setReceiptNo(requestDTO.getReceiptNo());
        salesInvoice.setLocation(Location.valueOf(requestDTO.getLocation().toUpperCase(Locale.ENGLISH)));
        salesInvoice.setRemarks(requestDTO.getRemarks());
        return salesInvoice;
    }

    public List<SalesInvoiceLine> toSalesInvoiceLines(List<SalesInvoiceLineRequestDTO> requestDTOs, List<Item> items) {
        List<SalesInvoiceLine> salesInvoiceLines = new ArrayList<SalesInvoiceLine>();

        for (SalesInvoiceLineRequestDTO sil : requestDTOs) {
            SalesInvoiceLine invoiceLine = new SalesInvoiceLine();
            Item item = items.stream().filter(i -> i.getId().equals(sil.getItemId())).findFirst().get();
            invoiceLine.setItem(item);
            invoiceLine.setQuantity(sil.getQuantity());
            salesInvoiceLines.add(invoiceLine);
        }
        return salesInvoiceLines;
    }

    public ListSalesInvoiceResponseDTO toListSalesInvoiceResponseDTO(Page<SalesInvoice> sis) {
        ListSalesInvoiceResponseDTO responseDTO = new ListSalesInvoiceResponseDTO();

        PaginationDTO paginationDetails = PaginationDTO.getInstance(sis.getTotalElements(), sis.getSize(),
                sis.getTotalPages(), sis.getNumber());
        responseDTO.setPagination(paginationDetails);

        for (SalesInvoice si : sis.getContent()) {
            responseDTO.getSalesInvoices().add(this.toLightGetSalesInvoiceResponseDTO(si));
        }

        return responseDTO;
    }

    public GetSalesInvoiceResponseDTO toLightGetSalesInvoiceResponseDTO(SalesInvoice salesInvoice) {
        GetSalesInvoiceResponseDTO responseDTO = new GetSalesInvoiceResponseDTO();

        responseDTO.setId(salesInvoice.getId());
        responseDTO.setCustomer(customerTransformer.toGetCustomerResponseDTO(salesInvoice.getCustomer()));
        responseDTO.setReceiptNo(salesInvoice.getReceiptNo());
        responseDTO.setLocation(salesInvoice.getLocation().getLabel());
        responseDTO.setSoldDate(salesInvoice.getSoldDate().toDate());
        responseDTO.setRemarks(salesInvoice.getRemarks());
        return responseDTO;
    }

    public GetSalesInvoiceResponseDTO toGetSalesInvoiceResponseDTO(SalesInvoice salesInvoice) {
        GetSalesInvoiceResponseDTO responseDTO = toLightGetSalesInvoiceResponseDTO(salesInvoice);

        for (SalesInvoiceLine sil : salesInvoice.getSalesInvoiceLines()) {
            responseDTO.getLines().add(this.toSalesInvoiceLineResponseDTO(sil));
        }
        return responseDTO;
    }

    public SalesInvoiceLineResponseDTO toSalesInvoiceLineResponseDTO(SalesInvoiceLine salesInvoiceLine) {
        SalesInvoiceLineResponseDTO responseDTO = new SalesInvoiceLineResponseDTO();

        responseDTO.setId(salesInvoiceLine.getId());
        responseDTO.setItem(itemTransformer.toGetItemResponseDTO(salesInvoiceLine.getItem()));
        responseDTO.setQuantity(salesInvoiceLine.getQuantity());
        return responseDTO;
    }

}
