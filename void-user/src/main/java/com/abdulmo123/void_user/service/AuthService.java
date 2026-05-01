package com.abdulmo123.void_user.service;

import com.abdulmo123.void_user.dto.LoginRequest;
import com.abdulmo123.void_user.dto.AuthResponse;
import com.abdulmo123.void_user.dto.RegisterRequest;


public interface AuthService {

    AuthResponse signup(RegisterRequest registerRequest);

    AuthResponse authenticate(LoginRequest loginRequest);

    Long validate(String authHeader);
}
