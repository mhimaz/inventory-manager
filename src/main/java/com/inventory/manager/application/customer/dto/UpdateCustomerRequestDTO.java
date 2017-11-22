package com.inventory.manager.application.customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateCustomerRequestDTO {

    private String name;

    private String contactNo;

    private String email;

    private String address;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CreateCustomerRequestDTO ");
        sb.append("{name=").append(name);
        sb.append(", contactNo=").append(contactNo);
        sb.append(", email=").append(email);
        sb.append(", address=").append(address);
        sb.append('}');
        return sb.toString();
    }

}
