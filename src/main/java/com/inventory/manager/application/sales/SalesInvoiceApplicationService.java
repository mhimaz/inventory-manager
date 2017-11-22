package com.inventory.manager.application.sales;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.manager.application.sales.dto.CreateSalesInvoiceRequestDTO;
import com.inventory.manager.application.sales.dto.GetSalesInvoiceResponseDTO;
import com.inventory.manager.application.sales.dto.ListSalesInvoiceResponseDTO;
import com.inventory.manager.application.sales.dto.SalesInvoiceLineRequestDTO;
import com.inventory.manager.domain.customer.Customer;
import com.inventory.manager.domain.customer.CustomerService;
import com.inventory.manager.domain.item.Item;
import com.inventory.manager.domain.item.ItemService;
import com.inventory.manager.domain.stockhistory.sales.SalesInvoice;
import com.inventory.manager.domain.stockhistory.sales.SalesInvoiceLine;
import com.inventory.manager.domain.stockhistory.sales.SalesInvoiceService;
import com.inventory.manager.exception.NotFoundException;

@Service
public class SalesInvoiceApplicationService {

    private static final Logger logger = Logger.getLogger(SalesInvoiceApplicationService.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private SalesInvoiceTransformer salesInvoiceTransformer;

    @Autowired
    private SalesInvoiceService salesInvoiceService;

    @Transactional
    public Integer createSalesInvoice(CreateSalesInvoiceRequestDTO requestDTO) {
        logger.info("[ Create Sales Invoice :: " + requestDTO + " ]");

        Customer customer = customerService.findCustomerById(requestDTO.getCustomerId());
        List<Integer> itemIds = requestDTO.getSalesInvoiceLines().stream().map(SalesInvoiceLineRequestDTO::getItemId)
                .collect(Collectors.toList());
        List<Item> items = itemService.findByItemIds(itemIds);

        if (items.size() != itemIds.size()) {
            throw new NotFoundException("Item not found for one of the item ids: " + itemIds);
        }

        SalesInvoice salesInvoice = salesInvoiceTransformer.toSalesInvoice(requestDTO, customer);

        List<SalesInvoiceLine> salesInvoiceLines = salesInvoiceTransformer.toSalesInvoiceLines(
                requestDTO.getSalesInvoiceLines(), items);

        return salesInvoiceService.createSalesInvoice(salesInvoice, salesInvoiceLines);
    }

    @Transactional
    public ListSalesInvoiceResponseDTO listSalesInvoices(String keyword, int page, int count, String sortDirection,
            String sortBy) {

        Page<SalesInvoice> sis = salesInvoiceService.findAll(keyword, page, count, sortDirection, sortBy);

        return salesInvoiceTransformer.toListSalesInvoiceResponseDTO(sis);
    }

    @Transactional
    public ListSalesInvoiceResponseDTO listSalesInvoicesByCustomer(Integer customerId, String keyword, int page,
            int count, String sortDirection, String sortBy) {

        Page<SalesInvoice> sis = salesInvoiceService.findAllByCustomer(customerId, keyword, page, count, sortDirection,
                sortBy);

        return salesInvoiceTransformer.toListSalesInvoiceResponseDTO(sis);
    }

    @Transactional
    public GetSalesInvoiceResponseDTO getSalesInvoice(Integer id) {

        SalesInvoice si = salesInvoiceService.findById(id);
        if (si == null) {
            throw new NotFoundException("Sales invoice not found for the id: " + id);
        }
        return salesInvoiceTransformer.toGetSalesInvoiceResponseDTO(si);
    }

    @Transactional
    public void deleteSalesInvoice(Integer id) {

        SalesInvoice si = salesInvoiceService.findById(id);
        if (si == null) {
            throw new NotFoundException("Sales invoice not found for the id: " + id);
        }

        salesInvoiceService.delete(si);
    }

}
