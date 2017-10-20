package com.inventory.manager.application.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inventory.manager.application.customer.dto.CreateCustomerRequestDTO;
import com.inventory.manager.application.customer.dto.UpdateCustomerRequestDTO;
import com.inventory.manager.domain.customer.Customer;
import com.inventory.manager.domain.customer.CustomerService;
import com.inventory.manager.exception.CustomExceptionCodes;
import com.inventory.manager.exception.DuplicateException;
import com.inventory.manager.exception.NotFoundException;

@Component
public class CustomerSpecification {

    @Autowired
    private CustomerService customerService;

    public boolean isSatisfiedBy(CreateCustomerRequestDTO requestDTO) {

        if (customerService.isCustomerNameExist(requestDTO.getName())) {
            throw new DuplicateException(CustomExceptionCodes.DUPLICATE_CUSTOMER_NAME.name(),
                    "Duplicate customer name: " + requestDTO.getName());
        }

        return true;
    }

    public boolean isSatisfiedBy(UpdateCustomerRequestDTO requestDTO, Customer customer) {

        if (customer == null) {
            throw new NotFoundException(CustomExceptionCodes.CUSTOMER_NOT_FOUND.name(), "Customer not found");
        }

        if (!customer.getName().equals(requestDTO.getName())
                && customerService.isCustomerNameExist(requestDTO.getName())) {
            throw new DuplicateException(CustomExceptionCodes.DUPLICATE_CUSTOMER_NAME.name(),
                    "Duplicate customer name: " + requestDTO.getName());
        }

        return true;
    }

}
