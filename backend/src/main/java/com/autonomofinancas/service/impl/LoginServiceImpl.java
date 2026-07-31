package com.autonomofinancas.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.autonomofinancas.config.JwtProperties;
import com.autonomofinancas.dto.request.LoginRequest;
import com.autonomofinancas.dto.response.LoginResponse;
import com.autonomofinancas.security.JwtService;
import com.autonomofinancas.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    public LoginServiceImpl(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            JwtProperties jwtProperties) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public LoginResponse autenticar(LoginRequest request) {
        UsernamePasswordAuthenticationToken credenciais = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getSenha());

        Authentication authentication = authenticationManager.authenticate(credenciais);

        String accessToken = jwtService.gerarToken(authentication);

        return new LoginResponse(
                accessToken,
                "Bearer",
                jwtProperties.expiration());
    }

}
