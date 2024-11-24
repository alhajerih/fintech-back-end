package com.springboot.bankbackend.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class FixedPaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long Id;

    private String name;

    private Double amount;

    public FixedPaymentEntity() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    @OneToMany
    @JoinColumn(name = "fixed_payment_id")
    private List<TransactionEntity> transactions;

}
