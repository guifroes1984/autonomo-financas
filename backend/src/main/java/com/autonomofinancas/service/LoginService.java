package com.autonomofinancas.service;

import com.autonomofinancas.dto.request.LoginRequest;
import com.autonomofinancas.dto.response.LoginResponse;

public interface LoginService {
    
    LoginResponse autenticar(LoginRequest request);

}
