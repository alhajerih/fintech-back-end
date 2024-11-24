package com.springboot.bankbackend.bo;

public class BeneficiaryRequest {
    private Long id;
    private String name;
    private Boolean doesNeedGroceries;
    private Double groceriesMultiplier;

    public BeneficiaryRequest() {}

    public BeneficiaryRequest(Long id, String name, Boolean doesNeedGroceries, Double groceriesMultiplier) {
        this.id = id;
        this.name = name;
        this.doesNeedGroceries = doesNeedGroceries;
        this.groceriesMultiplier = groceriesMultiplier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDoesNeedGroceries() {
        return doesNeedGroceries;
    }

    public void setDoesNeedGroceries(Boolean doesNeedGroceries) {
        this.doesNeedGroceries = doesNeedGroceries;
    }

    public Double getGroceriesMultiplier() {
        return groceriesMultiplier;
    }

    public void setGroceriesMultiplier(Double groceriesMultiplier) {
        this.groceriesMultiplier = groceriesMultiplier;
    }
}
