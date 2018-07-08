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

import com.inventory.manager.application.location.LocationApplicationService;
import com.inventory.manager.application.location.dto.CreateLocationRequestDTO;
import com.inventory.manager.application.location.dto.GetLocationResponseDTO;
import com.inventory.manager.application.location.dto.ListLocationResponseDTO;
import com.inventory.manager.application.location.dto.UpdateLocationRequestDTO;
import com.inventory.manager.exception.BadRequestException;
import com.inventory.manager.exception.ConflictException;
import com.inventory.manager.exception.DuplicateException;
import com.inventory.manager.exception.HTTPResponseHandler;
import com.inventory.manager.exception.NotFoundException;
import com.inventory.manager.util.AdapterErrorHandlerUtil;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class LocationController extends HTTPResponseHandler {

    private static final Logger logger = Logger.getLogger(LocationController.class);

    @Autowired
    AdapterErrorHandlerUtil adapterErrorHandlerUtil;

    @Autowired
    private LocationApplicationService locationAppSvc;

    @ResponseBody
    @RequestMapping(value = "location/create", method = RequestMethod.POST)
    public Integer createLocation(@RequestBody CreateLocationRequestDTO requestDTO, HttpServletResponse response) {

        Integer locationId = null;
        setStatusHeadersToSuccess(response);
        try {

            locationId = locationAppSvc.createLocation(requestDTO);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while creating location", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while creating location", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }

        return locationId;
    }

    @ResponseBody
    @RequestMapping(value = "location/{id}", method = RequestMethod.PUT)
    public void updateLocation(@PathVariable(value = "id") Integer locationId,
            @RequestBody UpdateLocationRequestDTO requestDTO, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        try {

            locationAppSvc.updateLocation(locationId, requestDTO);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while updating location", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while updating location", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
    }

    @ResponseBody
    @RequestMapping(value = "locations", method = RequestMethod.GET)
    public ListLocationResponseDTO listLocation(
            @RequestParam(value = "q", required = false, defaultValue = "") String query,
            @RequestParam(value = "sortby", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortdirection", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(value = "count", required = true) Integer count,
            @RequestParam(value = "page", required = true) Integer page, HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        ListLocationResponseDTO responseDTO = null;
        try {

            responseDTO = locationAppSvc.listLocations(query, page, count, sortDirection, sortBy);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while listing locations", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while listing locations", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;

    }

    @ResponseBody
    @RequestMapping(value = "location/{id}", method = RequestMethod.GET)
    public GetLocationResponseDTO getLocation(@PathVariable(value = "id") Integer locationId,
            HttpServletResponse response) {

        setStatusHeadersToSuccess(response);
        GetLocationResponseDTO responseDTO = null;
        try {

            responseDTO = locationAppSvc.getLocation(locationId);

        } catch (BadRequestException | DuplicateException | NotFoundException | ConflictException e) {
            logger.error("Error occurred while retreiving location", e);
            adapterErrorHandlerUtil.handleHTTPStatusCodeException(e);

        } catch (Exception e) {
            logger.error("Error occurred while retreiving location", e);
            adapterErrorHandlerUtil.throwInternalServerErrorException();
        }
        return responseDTO;
    }

}
