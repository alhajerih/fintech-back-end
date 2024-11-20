package com.springboot.bankbackend.service;

import com.springboot.bankbackend.bo.UserResponse;
import com.springboot.bankbackend.entity.TransactionsEntity;
import java.util.List;

public interface AdminService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse deleteUserById(Long id);
    List<TransactionsEntity> getAllDeposits();

}
