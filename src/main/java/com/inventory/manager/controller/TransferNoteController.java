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

import com.inventory.manager.application.transfer.TransferNoteApplicationService;
import com.inventory.manager.application.transfer.dto.CreateTransferNoteRequestDTO;
import com.inventory.manager.application.transfer.dto.GetTransferNoteResponseDTO;
import com.inventory.manager.application.transfer.dto.ListTransferNoteResponseDTO;
import com.inventory.manager.exception.BadRequestException;
import com.inventory.manager.exception.ConflictException;
import com.inventory.manager.exception.DuplicateException;
import com.inventory.manager.exception.HTTPResponseHandler;
import com.inventory.manager.exception.NotFoundException;
import com.inventory.manager.util.AdapterErrorHandlerUtil;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TransferNoteController extends HTTPResponseHandler {

    @Autowired
    private TransferNoteApplicationService transferNoteAppSvc;

    @Autowired
    AdapterErrorHandlerUtil adapterErrorHandlerUtil;

    private static final Logger logger = Logger.getLogger(TransferNoteController.class);

    @ResponseBody
    @RequestMapping(value = "transfernote/create", method = RequestMethod.POST)
    public void createTransferNote(@RequestBody CreateTransferNoteRequestDTO requestDTO, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        try {

            transferNoteAppSvc.createTransferNote(requestDTO);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while creating tansfer note", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while creating transfer note", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
    }

    @ResponseBody
    @RequestMapping(value = "transfernotes", method = RequestMethod.GET)
    public ListTransferNoteResponseDTO listTransferNotes(
            @RequestParam(value = "q", required = false, defaultValue = "") String query,
            @RequestParam(value = "sortby", required = false, defaultValue = "transferredDate") String sortBy,
            @RequestParam(value = "sortdirection", required = false, defaultValue = "DESC") String sortDirection,
            @RequestParam(value = "count", required = true) Integer count,
            @RequestParam(value = "page", required = true) Integer page, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        ListTransferNoteResponseDTO responseDTO = null;
        try {

            responseDTO = transferNoteAppSvc.listTransferNotes(query, page, count, sortDirection, sortBy);

        } catch (BadRequestException | DuplicateException | NotFoundException e) {
            logger.error("Error occurred while listing transfer notes", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while listing transfer notes", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping(value = "transfernote/{id}", method = RequestMethod.GET)
    public GetTransferNoteResponseDTO getTransferNote(@PathVariable("id") Integer id, HttpServletResponse response) {
        setStatusHeadersToSuccess(response);
        GetTransferNoteResponseDTO responseDTO = null;

        try {
            responseDTO = transferNoteAppSvc.getTransferNote(id);

        } catch (BadRequestException | DuplicateException | NotFoundException e) {
            logger.error("Error occurred while retrieving transfer note", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while retrieving transfer note", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;
    }

    @RequestMapping(value = "transfernote/{id}", method = RequestMethod.DELETE)
    public void deleteTransferNote(@PathVariable("id") Integer id, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        try {
            transferNoteAppSvc.deleteTransferNote(id);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while deleting transfer note", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while deleting transfer note", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
    }

}
