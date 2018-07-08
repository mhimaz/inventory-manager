package com.inventory.manager.application.location.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateLocationRequestDTO {

    private String name;

    private String address;

    private String contactNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CreateLocationRequestDTO ");
        sb.append(", name=").append(name);
        sb.append(", address=").append(address);
        sb.append(", contactNo=").append(contactNo);
        sb.append('}');
        return sb.toString();
    }

}
