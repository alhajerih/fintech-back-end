package com.springboot.bankbackend.bo;

import javax.validation.constraints.NotNull;

import javax.validation.constraints.NotBlank;

public class BeneficiaryRequest {
    @NotNull
    @NotBlank(message = "Username must not be blank")
    private String name;
    @NotNull
    private Boolean doesNeedGroceries;
    @NotNull
    private Double groceriesMultiplier;

    public BeneficiaryRequest() {}

    public BeneficiaryRequest(String name, Boolean doesNeedGroceries, Double groceriesMultiplier) {
        this.name = name;
        this.doesNeedGroceries = doesNeedGroceries;
        this.groceriesMultiplier = groceriesMultiplier;
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
