package com.autonomofinancas.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.autonomofinancas.dto.request.LoginRequest;
import com.autonomofinancas.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;

    public LoginServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication autenticar(LoginRequest request) {
        UsernamePasswordAuthenticationToken credenciais = 
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(), 
                    request.getSenha());

        return authenticationManager.authenticate(credenciais);
    }
    
}
