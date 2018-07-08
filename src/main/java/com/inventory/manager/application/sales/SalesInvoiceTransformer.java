package com.inventory.manager.application.sales;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.inventory.manager.application.customer.CustomerTransformer;
import com.inventory.manager.application.item.ItemTransformer;
import com.inventory.manager.application.location.LocationTransformer;
import com.inventory.manager.application.sales.dto.CreateSalesInvoiceRequestDTO;
import com.inventory.manager.application.sales.dto.GetSalesInvoiceResponseDTO;
import com.inventory.manager.application.sales.dto.ListSalesInvoiceResponseDTO;
import com.inventory.manager.application.sales.dto.SalesInvoiceLineRequestDTO;
import com.inventory.manager.application.sales.dto.SalesInvoiceLineResponseDTO;
import com.inventory.manager.application.shared.dto.PaginationDTO;
import com.inventory.manager.domain.customer.Customer;
import com.inventory.manager.domain.enums.DiscountMode;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.location.Location;
import com.inventory.manager.domain.stockhistory.sales.SalesInvoice;
import com.inventory.manager.domain.stockhistory.sales.SalesInvoiceLine;

@Component
public class SalesInvoiceTransformer {

    @Autowired
    private CustomerTransformer customerTransformer;

    @Autowired
    private ItemTransformer itemTransformer;

    @Autowired
    private LocationTransformer locationTransformer;

    public SalesInvoice toSalesInvoice(CreateSalesInvoiceRequestDTO requestDTO, Customer customer, Location location) {
        SalesInvoice salesInvoice = new SalesInvoice();
        salesInvoice.setCustomer(customer);
        salesInvoice.setSoldDate(new DateTime(requestDTO.getSoldDate()));
        salesInvoice.setReceiptNo(requestDTO.getReceiptNo());
        salesInvoice.setLocation(location);
        salesInvoice.setRemarks(requestDTO.getRemarks());
        salesInvoice.setIsReturn(requestDTO.isReturn());
        return salesInvoice;
    }

    public List<SalesInvoiceLine> toSalesInvoiceLines(List<SalesInvoiceLineRequestDTO> requestDTOs, List<Item> items) {
        List<SalesInvoiceLine> salesInvoiceLines = new ArrayList<>();

        for (SalesInvoiceLineRequestDTO sil : requestDTOs) {
            SalesInvoiceLine invoiceLine = new SalesInvoiceLine();
            Item item = items.stream().filter(i -> i.getId().equals(sil.getItemId())).findFirst().get();

            DiscountMode discountMode = null;
            if (StringUtils.hasText(sil.getDiscountMode())) {
                discountMode = DiscountMode.valueOf(sil.getDiscountMode().toUpperCase(Locale.ENGLISH));
            }
            invoiceLine.setItem(item);
            invoiceLine.setQuantity(sil.getQuantity());
            invoiceLine.setDiscountMode(discountMode);
            invoiceLine.setDiscountValue(BigDecimal.valueOf(sil.getDiscountValue()));

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
        responseDTO.setLocation(locationTransformer.toGetLocationResponseDTO(salesInvoice.getLocation()));
        responseDTO.setSoldDate(salesInvoice.getSoldDate().toDate());
        responseDTO.setRemarks(salesInvoice.getRemarks());
        responseDTO.setIsReturn(salesInvoice.getIsReturn());
        responseDTO.setGrandTotal(salesInvoice.getGrandTotal().doubleValue());
        responseDTO.setDiscountTotal(salesInvoice.getDiscountTotal().doubleValue());
        responseDTO.setNetTotal(salesInvoice.getNetTotal().doubleValue());

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
        if (salesInvoiceLine.getDiscountMode() != null) {
            responseDTO.setDiscountMode(salesInvoiceLine.getDiscountMode().getLabel());
        }
        responseDTO.setDiscountValue(salesInvoiceLine.getDiscountValue().doubleValue());
        responseDTO.setGrandTotal(salesInvoiceLine.getGrandTotal().doubleValue());
        responseDTO.setDiscountTotal(salesInvoiceLine.getDiscountTotal().doubleValue());
        responseDTO.setNetTotal(salesInvoiceLine.getNetTotal().doubleValue());

        return responseDTO;
    }

}
