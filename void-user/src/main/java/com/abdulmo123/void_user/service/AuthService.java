package com.abdulmo123.void_user.service;

import com.abdulmo123.void_user.dto.LoginRequest;
import com.abdulmo123.void_user.dto.AuthResponse;
import com.abdulmo123.void_user.dto.RegisterRequest;
import com.abdulmo123.void_user.dto.UserMeProfileDto;
import com.abdulmo123.void_user.model.User;


public interface AuthService {

    AuthResponse signup(RegisterRequest registerRequest);

    AuthResponse authenticate(LoginRequest loginRequest);

    UserMeProfileDto validate(String authHeader);
}
