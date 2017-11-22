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

import com.inventory.manager.application.grn.GRNApplicationService;
import com.inventory.manager.application.grn.dto.CreateGoodReceiveNoteRequestDTO;
import com.inventory.manager.application.grn.dto.GetGoodReceiveNoteResponseDTO;
import com.inventory.manager.application.grn.dto.ListGoodReceiveNoteResponseDTO;
import com.inventory.manager.exception.BadRequestException;
import com.inventory.manager.exception.ConflictException;
import com.inventory.manager.exception.DuplicateException;
import com.inventory.manager.exception.HTTPResponseHandler;
import com.inventory.manager.exception.NotFoundException;
import com.inventory.manager.util.AdapterErrorHandlerUtil;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GoodReceiveNoteController extends HTTPResponseHandler {

    @Autowired
    private GRNApplicationService grnAppSvc;

    @Autowired
    AdapterErrorHandlerUtil adapterErrorHandlerUtil;

    private static final Logger logger = Logger.getLogger(GoodReceiveNoteController.class);

    @ResponseBody
    @RequestMapping(value = "grn/create", method = RequestMethod.POST)
    public void createGRN(@RequestBody CreateGoodReceiveNoteRequestDTO requestDTO, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        try {

            grnAppSvc.createGRN(requestDTO);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while creating GRN", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while creating GRN", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
    }

    @ResponseBody
    @RequestMapping(value = "grns", method = RequestMethod.GET)
    public ListGoodReceiveNoteResponseDTO listGRN(
            @RequestParam(value = "q", required = false, defaultValue = "") String query,
            @RequestParam(value = "sortby", required = false, defaultValue = "purchasedDate") String sortBy,
            @RequestParam(value = "sortdirection", required = false, defaultValue = "DESC") String sortDirection,
            @RequestParam(value = "count", required = true) Integer count,
            @RequestParam(value = "page", required = true) Integer page,
            @RequestParam(value = "supplierid", required = false) Integer supplierId, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        ListGoodReceiveNoteResponseDTO responseDTO = null;
        try {

            if (supplierId != null) {
                responseDTO = grnAppSvc.listGRNsBySupplier(supplierId, query, page, count, sortDirection, sortBy);
            } else {
                responseDTO = grnAppSvc.listGRNs(query, page, count, sortDirection, sortBy);
            }

        } catch (BadRequestException | DuplicateException | NotFoundException e) {
            logger.error("Error occurred while listing grns", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while listing grns", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping(value = "grn/{id}", method = RequestMethod.GET)
    public GetGoodReceiveNoteResponseDTO getGRN(@PathVariable("id") Integer id, HttpServletResponse response) {
        setStatusHeadersToSuccess(response);
        GetGoodReceiveNoteResponseDTO responseDTO = null;

        try {
            responseDTO = grnAppSvc.getGRN(id);

        } catch (BadRequestException | DuplicateException | NotFoundException e) {
            logger.error("Error occurred while retrieving grn", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while retrieving grns", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;
    }

    @RequestMapping(value = "grn/{id}", method = RequestMethod.DELETE)
    public void deleteGRN(@PathVariable("id") Integer id, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        try {
            grnAppSvc.deleteGRN(id);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while deleting grn", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while deleting grns", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
    }

}
