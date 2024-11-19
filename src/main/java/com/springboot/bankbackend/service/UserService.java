package com.springboot.bankbackend.service;

import com.springboot.bankbackend.bo.*;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    UserResponse updateProfile(UpdateProfileRequest request);
    UserResponse getProfile(String filterBefore, String filterAfter);
    public UpdateBalanceResponse updateBalance(UpdateBalanceRequest request);
    //    UserBalanceResponse withdraw(UserTransactionRequest request);
}
