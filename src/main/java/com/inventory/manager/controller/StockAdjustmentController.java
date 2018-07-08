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

import com.inventory.manager.application.adjustment.StockAdjustmentApplicationService;
import com.inventory.manager.application.adjustment.dto.CreateStockAdjustmentRequestDTO;
import com.inventory.manager.application.adjustment.dto.GetStockAdjustmentResponseDTO;
import com.inventory.manager.application.adjustment.dto.ListStockAdjustmentResponseDTO;
import com.inventory.manager.exception.BadRequestException;
import com.inventory.manager.exception.ConflictException;
import com.inventory.manager.exception.DuplicateException;
import com.inventory.manager.exception.HTTPResponseHandler;
import com.inventory.manager.exception.NotFoundException;
import com.inventory.manager.util.AdapterErrorHandlerUtil;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class StockAdjustmentController extends HTTPResponseHandler {

    @Autowired
    private StockAdjustmentApplicationService stockAdjustmentAppSvc;

    @Autowired
    AdapterErrorHandlerUtil adapterErrorHandlerUtil;

    private static final Logger logger = Logger.getLogger(StockAdjustmentController.class);

    @ResponseBody
    @RequestMapping(value = "stockadjustment/create", method = RequestMethod.POST)
    public void createStockAdjustment(@RequestBody CreateStockAdjustmentRequestDTO requestDTO,
            HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        try {

            stockAdjustmentAppSvc.createStockAdjustment(requestDTO);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while creating stock adjustment", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while creating stock adjustment", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
    }

    @ResponseBody
    @RequestMapping(value = "stockadjustments", method = RequestMethod.GET)
    public ListStockAdjustmentResponseDTO listStockAdjustments(
            @RequestParam(value = "q", required = false, defaultValue = "") String query,
            @RequestParam(value = "sortby", required = false, defaultValue = "adjustedDate") String sortBy,
            @RequestParam(value = "sortdirection", required = false, defaultValue = "DESC") String sortDirection,
            @RequestParam(value = "count", required = true) Integer count,
            @RequestParam(value = "page", required = true) Integer page, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        ListStockAdjustmentResponseDTO responseDTO = null;
        try {

            responseDTO = stockAdjustmentAppSvc.listStockAdjustments(query, page, count, sortDirection, sortBy);

        } catch (BadRequestException | DuplicateException | NotFoundException e) {
            logger.error("Error occurred while listing stock adjustments", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while listing stock adjustments", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping(value = "stockadjustment/{id}", method = RequestMethod.GET)
    public GetStockAdjustmentResponseDTO getStockAdjustment(@PathVariable("id") Integer id, HttpServletResponse response) {
        setStatusHeadersToSuccess(response);
        GetStockAdjustmentResponseDTO responseDTO = null;

        try {
            responseDTO = stockAdjustmentAppSvc.getStockAdjustment(id);

        } catch (BadRequestException | DuplicateException | NotFoundException e) {
            logger.error("Error occurred while retrieving stock adjustment", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while retrieving stock adjustment", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;
    }

}
