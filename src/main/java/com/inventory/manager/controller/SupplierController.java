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

import com.inventory.manager.application.supplier.SupplierApplicationService;
import com.inventory.manager.application.supplier.dto.CreateSupplierRequestDTO;
import com.inventory.manager.application.supplier.dto.ListSuppliersResponseDTO;
import com.inventory.manager.application.supplier.dto.UpdateSupplierRequestDTO;
import com.inventory.manager.exception.BadRequestException;
import com.inventory.manager.exception.ConflictException;
import com.inventory.manager.exception.DuplicateException;
import com.inventory.manager.exception.HTTPResponseHandler;
import com.inventory.manager.exception.NotFoundException;
import com.inventory.manager.util.AdapterErrorHandlerUtil;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SupplierController extends HTTPResponseHandler {

    @Autowired
    private SupplierApplicationService supplierApplicationService;

    @Autowired
    AdapterErrorHandlerUtil adapterErrorHandlerUtil;

    private static final Logger logger = Logger.getLogger(CustomerController.class);

    @ResponseBody
    @RequestMapping(value = "supplier/create", method = RequestMethod.POST)
    public Integer createSupplier(@RequestBody CreateSupplierRequestDTO requestDTO, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        Integer id = null;
        try {

            id = supplierApplicationService.createSupplier(requestDTO);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while creating supplier", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while creating supplier", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }

        return id;
    }

    @ResponseBody
    @RequestMapping(value = "supplier/{id}", method = RequestMethod.PUT)
    public void updateSupplier(@PathVariable(value = "id") Integer supplierId,
            @RequestBody UpdateSupplierRequestDTO requestDTO, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        try {

            supplierApplicationService.updateSupplier(supplierId, requestDTO);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while updating supplier", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while updating supplier", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
    }

    @ResponseBody
    @RequestMapping(value = "allsuppliers", method = RequestMethod.GET)
    public ListSuppliersResponseDTO getAllSuppliers(HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        ListSuppliersResponseDTO responseDTO = null;

        try {

            responseDTO = supplierApplicationService.getAllSuppliers();

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while retrieving all supplier", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while retrieving all supplier", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping(value = "suppliers", method = RequestMethod.GET)
    public ListSuppliersResponseDTO getSuppliers(
            @RequestParam(value = "q", required = false, defaultValue = "") String query,
            @RequestParam(value = "sortby", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortdirection", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(value = "count", required = true) Integer count,
            @RequestParam(value = "page", required = true) Integer page, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        ListSuppliersResponseDTO responseDTO = null;

        try {

            responseDTO = supplierApplicationService.listSuppliers(query, page, count, sortDirection, sortBy);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while listing suppliers", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while listing suppliers", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping(value = "supplier/{id}", method = RequestMethod.DELETE)
    public void deleteSupplier(@PathVariable(value = "id") Integer supplierId, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        try {

            supplierApplicationService.deleteSupplier(supplierId);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while deleting supplier", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while deleting supplier", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
    }

}
