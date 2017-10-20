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

import com.inventory.manager.application.customer.CustomerApplicationService;
import com.inventory.manager.application.customer.dto.CreateCustomerRequestDTO;
import com.inventory.manager.application.customer.dto.ListCustomersResponseDTO;
import com.inventory.manager.application.customer.dto.UpdateCustomerRequestDTO;
import com.inventory.manager.exception.BadRequestException;
import com.inventory.manager.exception.ConflictException;
import com.inventory.manager.exception.DuplicateException;
import com.inventory.manager.exception.HTTPResponseHandler;
import com.inventory.manager.exception.NotFoundException;
import com.inventory.manager.util.AdapterErrorHandlerUtil;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController extends HTTPResponseHandler {

    @Autowired
    private CustomerApplicationService customerApplicationService;

    @Autowired
    AdapterErrorHandlerUtil adapterErrorHandlerUtil;

    private static final Logger logger = Logger.getLogger(CustomerController.class);

    @ResponseBody
    @RequestMapping(value = "customer/create", method = RequestMethod.POST)
    public Integer createCustomer(@RequestBody CreateCustomerRequestDTO requestDTO, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        Integer id = null;
        try {

            id = customerApplicationService.createCustomer(requestDTO);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while creating customer", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while creating customer", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return id;
    }

    @ResponseBody
    @RequestMapping(value = "customer/{id}", method = RequestMethod.PUT)
    public void updateCustomer(@PathVariable(value = "id") Integer customerId,
            @RequestBody UpdateCustomerRequestDTO requestDTO, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        try {

            customerApplicationService.updateCustomer(customerId, requestDTO);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while updating customer", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while updating customer", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
    }

    @ResponseBody
    @RequestMapping(value = "allcustomers", method = RequestMethod.GET)
    public ListCustomersResponseDTO getAllCustomers(HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        ListCustomersResponseDTO responseDTO = null;
        try {

            responseDTO = customerApplicationService.getAllCustomers();

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while retrieving all customer", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while retrieving all customer", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;

    }

    @ResponseBody
    @RequestMapping(value = "customers", method = RequestMethod.GET)
    public ListCustomersResponseDTO listCustomers(
            @RequestParam(value = "q", required = false, defaultValue = "") String query,
            @RequestParam(value = "sortby", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortdirection", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(value = "count", required = true) Integer count,
            @RequestParam(value = "page", required = true) Integer page, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        ListCustomersResponseDTO responseDTO = null;
        try {

            responseDTO = customerApplicationService.listCustomers(query, page, count, sortDirection, sortBy);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while listing customers", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while listing customers", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;

    }

    @ResponseBody
    @RequestMapping(value = "customer/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable(value = "id") Integer customerId, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        try {

            customerApplicationService.deleteCustomer(customerId);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while deleting customer", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while deleting customer", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
    }
}
