package com.springboot.bankbackend.service.auth;

import com.springboot.bankbackend.bo.auth.AuthenticationResponse;
import com.springboot.bankbackend.bo.auth.CreateLoginRequest;
import com.springboot.bankbackend.bo.auth.LogoutResponse;

public interface AuthService {
    AuthenticationResponse login(CreateLoginRequest createLoginRequest);
    void logout(LogoutResponse logoutResponse);
}
