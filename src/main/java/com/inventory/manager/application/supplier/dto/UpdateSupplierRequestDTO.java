package com.inventory.manager.application.supplier.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateSupplierRequestDTO {

    private String name;

    private String contactNo;

    private String email;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CreateSupplierRequestDTO ");
        sb.append("{name=").append(name);
        sb.append(", contactNo=").append(contactNo);
        sb.append(", email=").append(email);
        sb.append('}');
        return sb.toString();
    }

}
