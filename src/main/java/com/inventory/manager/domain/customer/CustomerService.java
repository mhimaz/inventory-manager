package com.inventory.manager.domain.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    public Customer createCustomer(Customer customer) {

        customer.setIsDeleted(false);
        return customerRepo.save(customer);

    }

    public Customer findCustomerById(Integer id) {

        return customerRepo.findByIdAndIsDeleted(id, false);
    }

    public void updateCustomer(Customer customer) {

        customerRepo.save(customer);
    }

    public void deleteCustomer(Customer customer) {

        customer.setIsDeleted(true);
        customerRepo.save(customer);
    }

    public Boolean isCustomerNameExist(String name) {

        Customer customer = customerRepo.findByNameAndIsDeleted(name, false);
        return customer != null;
    }

    public List<Customer> getAllCustomers() {

        return customerRepo.findByIsDeleted(false);
    }

    public Page<Customer> findAll(String keyword, Integer page, Integer count, String sortDirection, String sortBy) {

        Pageable pageable = new PageRequest(page, count, Direction.fromString(sortDirection), sortBy);
        Page<Customer> customers = customerRepo.findAll(keyword, pageable);
        return customers;
    }

}
