package com.inventory.manager.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inventory.manager.application.sales.SalesInvoiceApplicationService;
import com.inventory.manager.application.sales.dto.CreateSalesInvoiceRequestDTO;
import com.inventory.manager.application.sales.dto.GetSalesInvoiceResponseDTO;
import com.inventory.manager.application.sales.dto.ListSalesInvoiceResponseDTO;
import com.inventory.manager.application.shared.dto.CalculateTotalRequestDTO;
import com.inventory.manager.application.shared.dto.CalculateTotalResponseDTO;
import com.inventory.manager.exception.BadRequestException;
import com.inventory.manager.exception.ConflictException;
import com.inventory.manager.exception.DuplicateException;
import com.inventory.manager.exception.HTTPResponseHandler;
import com.inventory.manager.exception.NotFoundException;
import com.inventory.manager.util.AdapterErrorHandlerUtil;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SalesInvoiceController extends HTTPResponseHandler {

    @Autowired
    private SalesInvoiceApplicationService salesInvoiceAppSvc;

    @Autowired
    AdapterErrorHandlerUtil adapterErrorHandlerUtil;

    private static final Logger logger = Logger.getLogger(SalesInvoiceController.class);

    @ResponseBody
    @RequestMapping(value = "salesinvoice/create", method = RequestMethod.POST)
    public void createSalesInvoice(@RequestBody CreateSalesInvoiceRequestDTO requestDTO, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        try {

            salesInvoiceAppSvc.createSalesInvoice(requestDTO);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while creating sales invoice", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while creating sales invoice", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
    }

    @ResponseBody
    @RequestMapping(value = "salesinvoices", method = RequestMethod.GET)
    public ListSalesInvoiceResponseDTO listSalesIinvoices(
            @RequestParam(value = "q", required = false, defaultValue = "") String query,
            @RequestParam(value = "sortby", required = false, defaultValue = "soldDate") String sortBy,
            @RequestParam(value = "sortdirection", required = false, defaultValue = "DESC") String sortDirection,
            @RequestParam(value = "count", required = true) Integer count,
            @RequestParam(value = "page", required = true) Integer page,
            @RequestParam(value = "customerid", required = false) Integer customerId, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        ListSalesInvoiceResponseDTO responseDTO = null;
        try {

            if (customerId != null) {
                responseDTO = salesInvoiceAppSvc.listSalesInvoicesByCustomer(customerId, query, page, count,
                        sortDirection, sortBy);
            } else {
                responseDTO = salesInvoiceAppSvc.listSalesInvoices(query, page, count, sortDirection, sortBy);
            }

        } catch (BadRequestException | DuplicateException | NotFoundException e) {
            logger.error("Error occurred while listing sales invoices", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while listing sales invoicess", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping(value = "salesinvoice/{id}", method = RequestMethod.GET)
    public GetSalesInvoiceResponseDTO getSalesInvoice(@PathVariable("id") Integer id, HttpServletResponse response) {
        setStatusHeadersToSuccess(response);
        GetSalesInvoiceResponseDTO responseDTO = null;

        try {
            responseDTO = salesInvoiceAppSvc.getSalesInvoice(id);

        } catch (BadRequestException | DuplicateException | NotFoundException e) {
            logger.error("Error occurred while retrieving sales invoice", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while retrieving sales invoice", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;
    }

    @RequestMapping(value = "salesinvoice/{id}", method = RequestMethod.DELETE)
    public void deleteSalesInvoice(@PathVariable("id") Integer id, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        try {
            salesInvoiceAppSvc.deleteSalesInvoice(id);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while deleting sales invoice", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while deleting sales invoice", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
    }

    @ResponseBody
    @RequestMapping(value = "sales/calculatetotal", method = RequestMethod.POST)
    public CalculateTotalResponseDTO calculateTotal(@RequestBody CalculateTotalRequestDTO requestDTO,
            HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        CalculateTotalResponseDTO responseDTO = null;
        try {
            responseDTO = salesInvoiceAppSvc.calculateTotal(requestDTO);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while calculating total", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while calculating total", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;
    }

}
