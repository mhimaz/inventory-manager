package com.inventory.manager.application.customer;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.inventory.manager.application.customer.dto.CreateCustomerRequestDTO;
import com.inventory.manager.application.customer.dto.GetCustomerResponseDTO;
import com.inventory.manager.application.customer.dto.ListCustomersResponseDTO;
import com.inventory.manager.application.customer.dto.UpdateCustomerRequestDTO;
import com.inventory.manager.application.shared.dto.PaginationDTO;
import com.inventory.manager.domain.customer.Customer;

@Component
public class CustomerTransformer {

    public Customer toCustomer(CreateCustomerRequestDTO requestDTO) {
        Customer customer = new Customer();
        customer.setName(requestDTO.getName());
        customer.setContactNo(requestDTO.getContactNo());
        customer.setEmail(requestDTO.getEmail());
        customer.setAddress(requestDTO.getAddress());
        return customer;
    }

    public Customer toCustomer(UpdateCustomerRequestDTO requestDTO, Customer customer) {
        customer.setName(requestDTO.getName());
        customer.setContactNo(requestDTO.getContactNo());
        customer.setEmail(requestDTO.getEmail());
        customer.setAddress(requestDTO.getAddress());
        return customer;
    }

    public GetCustomerResponseDTO toGetCustomerResponseDTO(Customer customer) {
        GetCustomerResponseDTO customerDTO = new GetCustomerResponseDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setContactNo(customer.getContactNo());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setAddress(customer.getAddress());
        return customerDTO;
    }

    public ListCustomersResponseDTO toListCustomersResponseDTO(Page<Customer> customers) {
        ListCustomersResponseDTO responseDTO = new ListCustomersResponseDTO();

        PaginationDTO paginationDetails = PaginationDTO.getInstance(customers.getTotalElements(), customers.getSize(),
                customers.getTotalPages(), customers.getNumber());
        responseDTO.setPagination(paginationDetails);

        for (Customer customer : customers.getContent()) {
            responseDTO.getCustomers().add(this.toGetCustomerResponseDTO(customer));
        }

        return responseDTO;
    }

}
