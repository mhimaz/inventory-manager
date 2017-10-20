package com.inventory.manager.application.customer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inventory.manager.application.shared.dto.BaseResponseDTO;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class GetCustomerResponseDTO extends BaseResponseDTO {

    private Integer id;

    private String name;

    private String contactNo;

    private String email;

    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
