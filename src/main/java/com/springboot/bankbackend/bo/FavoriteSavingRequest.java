package com.springboot.bankbackend.bo;

public class FavoriteSavingRequest {
    private Long id;

    public FavoriteSavingRequest() {}

    public FavoriteSavingRequest(Long id) {this.id = id;}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }
}
