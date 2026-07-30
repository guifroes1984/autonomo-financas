package com.autonomofinancas.service;

import org.springframework.security.core.Authentication;

import com.autonomofinancas.dto.request.LoginRequest;

public interface LoginService {
    
    Authentication autenticar(LoginRequest request);

}
