package com.abdulmo123.void_user.service;

import com.abdulmo123.void_user.dto.LoginRequest;
import com.abdulmo123.void_user.dto.AuthResponseDto;
import com.abdulmo123.void_user.dto.RegisterRequest;
import com.abdulmo123.void_user.dto.UserMeProfileDto;


public interface AuthService {

    AuthResponseDto signup(RegisterRequest registerRequest);

    AuthResponseDto authenticate(LoginRequest loginRequest);

    UserMeProfileDto validate(String authHeader);
}
