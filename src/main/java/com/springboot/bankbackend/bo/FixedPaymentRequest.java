package com.springboot.bankbackend.bo;

import javax.validation.constraints.NotNull;

public class FixedPaymentRequest {
    @NotNull
    private String name;
    @NotNull
    private double amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
