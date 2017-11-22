package com.inventory.manager.application.customer;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.manager.application.customer.dto.CreateCustomerRequestDTO;
import com.inventory.manager.application.customer.dto.ListCustomersResponseDTO;
import com.inventory.manager.application.customer.dto.UpdateCustomerRequestDTO;
import com.inventory.manager.domain.customer.Customer;
import com.inventory.manager.domain.customer.CustomerService;
import com.inventory.manager.exception.CustomExceptionCodes;
import com.inventory.manager.exception.NotFoundException;

@Service
public class CustomerApplicationService {

    private static final Logger logger = Logger.getLogger(CustomerApplicationService.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerSpecification customerSpecification;

    @Autowired
    private CustomerTransformer customerTransformer;

    @Transactional
    public Integer createCustomer(CreateCustomerRequestDTO requestDTO) {
        logger.info("[ Creating Customer :: " + requestDTO + " ]");

        customerSpecification.isSatisfiedBy(requestDTO);

        Customer customer = customerTransformer.toCustomer(requestDTO);

        customer = customerService.createCustomer(customer);

        return customer.getId();

    }

    @Transactional
    public void updateCustomer(Integer customerId, UpdateCustomerRequestDTO requestDTO) {
        logger.info("[ Updating Customer :: customerId: " + customerId + ", requestDTO: " + requestDTO + " ]");

        Customer customer = customerService.findCustomerById(customerId);

        customerSpecification.isSatisfiedBy(requestDTO, customer);

        customer = customerTransformer.toCustomer(requestDTO, customer);

        customerService.updateCustomer(customer);

    }

    @Transactional
    public ListCustomersResponseDTO getAllCustomers() {
        logger.info("[ Get All Customers ]");
        ListCustomersResponseDTO responseDTO = new ListCustomersResponseDTO();

        List<Customer> customers = customerService.getAllCustomers();

        customers.forEach(customer -> {
            responseDTO.getCustomers().add(customerTransformer.toGetCustomerResponseDTO(customer));
        });

        return responseDTO;
    }

    @Transactional
    public ListCustomersResponseDTO listCustomers(String keyword, int page, int count, String sortDirection,
            String sortBy) {
        logger.info("[ List Customers ]");
        Page<Customer> customers = customerService.findAll(keyword, page, count, sortDirection, sortBy);

        return customerTransformer.toListCustomersResponseDTO(customers);
    }

    @Transactional
    public void deleteCustomer(Integer customerId) {
        logger.info("[ Deleting Customer :: customerId: " + customerId + "]");

        Customer customer = customerService.findCustomerById(customerId);

        if (customer == null) {
            throw new NotFoundException(CustomExceptionCodes.CUSTOMER_NOT_FOUND.name(), "Customer not found");
        }

        customerService.deleteCustomer(customer);

    }

}
