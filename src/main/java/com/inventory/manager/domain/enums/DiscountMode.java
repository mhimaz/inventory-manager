package com.inventory.manager.domain.enums;

public enum DiscountMode {
    
    VALUE("Value"), PERCENTAGE("Percentage");
    
    private String label;

    DiscountMode(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
