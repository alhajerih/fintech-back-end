package com.springboot.bankbackend.bo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class SavingRequest {
    private Long id;

    @NotNull
    @NotBlank(message = "Name must not be blank")
    private String name;
    @NotNull
    private Double amount;
    @NotNull
    private Double amountAllocatedPerMonth;
    @NotNull
    private Integer monthsUntilDeadline;

    public SavingRequest() {}

    public SavingRequest(Long id, String name, Double amount, Double amountAllocatedPerMonth, Integer monthsUntilDeadline) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.amountAllocatedPerMonth = amountAllocatedPerMonth;
        this.monthsUntilDeadline = monthsUntilDeadline;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmountAllocatedPerMonth() {
        return amountAllocatedPerMonth;
    }

    public void setAmountAllocatedPerMonth(Double amountAllocatedPerMonth) {
        this.amountAllocatedPerMonth = amountAllocatedPerMonth;
    }

    public Integer getMonthsUntilDeadline() {
        return monthsUntilDeadline;
    }

    public void setMonthsUntilDeadline(Integer monthsUntilDeadline) {
        this.monthsUntilDeadline = monthsUntilDeadline;
    }
}
