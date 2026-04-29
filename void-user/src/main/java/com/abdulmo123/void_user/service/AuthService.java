package com.abdulmo123.void_user.service;

import com.abdulmo123.void_user.dto.AuthRequest;
import com.abdulmo123.void_user.dto.AuthResponse;
import com.abdulmo123.void_user.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


public interface AuthService {

    AuthResponse signup(SignUpRequest signUpRequest);

    AuthResponse authenticate(AuthRequest signUpRequest);
}
