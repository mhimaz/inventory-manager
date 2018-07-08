package com.inventory.manager.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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

import com.inventory.manager.application.item.ItemApplicationService;
import com.inventory.manager.application.item.dto.CreateItemRequestDTO;
import com.inventory.manager.application.item.dto.GetItemResponseDTO;
import com.inventory.manager.application.item.dto.ListItemsResponseDTO;
import com.inventory.manager.application.item.dto.UpdateItemPriceRequestDTO;
import com.inventory.manager.application.item.dto.UpdateItemRequestDTO;
import com.inventory.manager.exception.BadRequestException;
import com.inventory.manager.exception.ConflictException;
import com.inventory.manager.exception.DuplicateException;
import com.inventory.manager.exception.HTTPResponseHandler;
import com.inventory.manager.exception.NotFoundException;
import com.inventory.manager.util.AdapterErrorHandlerUtil;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController extends HTTPResponseHandler {

    @Autowired
    private ItemApplicationService itemAppSvc;

    @Autowired
    AdapterErrorHandlerUtil adapterErrorHandlerUtil;

    private static final Logger logger = Logger.getLogger(ItemController.class);

    @ApiOperation(httpMethod = "POST", value = "Create item", nickname = "Create item")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = Integer.class),
        @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure"),
        @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseBody
    @RequestMapping(value = "item/create", method = RequestMethod.POST)
    public Integer createItem(@RequestBody CreateItemRequestDTO requestDTO, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        Integer itemId = null;
        try {

            itemId = itemAppSvc.createItem(requestDTO);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while creating item", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while creating item", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return itemId;
    }

    @ResponseBody
    @RequestMapping(value = "item/{id}", method = RequestMethod.PUT)
    public GetItemResponseDTO updateItem(@PathVariable(value = "id") Integer itemId,
            @RequestBody UpdateItemRequestDTO requestDTO, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        GetItemResponseDTO responseDTO = null;
        try {

            responseDTO = itemAppSvc.updateItem(itemId, requestDTO);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while updating item", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while updating item", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping(value = "item/{id}/price", method = RequestMethod.PUT)
    public GetItemResponseDTO updateItemPrice(@PathVariable(value = "id") Integer itemId,
            @RequestBody UpdateItemPriceRequestDTO requestDTO, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        GetItemResponseDTO responseDTO = null;
        try {

            responseDTO = itemAppSvc.updateItemPrice(itemId, requestDTO);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while updating item price", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while updating item price", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping(value = "items", method = RequestMethod.GET)
    public ListItemsResponseDTO listItem(@RequestParam(value = "q", required = false, defaultValue = "") String query,
            @RequestParam(value = "sortby", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortdirection", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(value = "count", required = true) Integer count,
            @RequestParam(value = "page", required = true) Integer page,
            @RequestParam(value = "supplierid", required = false) Integer supplierId, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        ListItemsResponseDTO responseDTO = null;
        try {

            if (supplierId != null) {
                responseDTO = itemAppSvc.listItemsBySupplier(supplierId, query, page, count, sortDirection, sortBy);
            } else {
                responseDTO = itemAppSvc.listItems(query, page, count, sortDirection, sortBy);
            }

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while listing items", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while listing items", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping(value = "supplier/{id}/items", method = RequestMethod.GET)
    public ListItemsResponseDTO getItemsBySupplier(@PathVariable(value = "id") Integer supplierId,
            HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        ListItemsResponseDTO responseDTO = null;
        try {

            responseDTO = itemAppSvc.getItemsBySupplier(supplierId);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while retreiving items by supplier", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while retreiving items by supplier", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping(value = "item/{id}", method = RequestMethod.DELETE)
    public void deleteItem(@PathVariable(value = "id") Integer itemId, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        try {

            itemAppSvc.deleteItem(itemId);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while deleting item", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while deleting item", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
    }
}
