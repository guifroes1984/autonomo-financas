package com.autonomofinancas.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.autonomofinancas.entity.Usuario;
import com.autonomofinancas.exception.RecursoNaoEncontradoException;
import com.autonomofinancas.repository.UsuarioRepository;
import com.autonomofinancas.service.UsuarioAutenticadoService;

@Service
public class UsuarioAutenticadoServiceImpl implements UsuarioAutenticadoService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioAutenticadoServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario obterUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null 
            || !authentication.isAuthenticated() 
            || "anonymousUser".equals(authentication.getCredentials())) {
            
            throw new RecursoNaoEncontradoException(
                "Usuário authenticado não encontrado.");
        }

        String email = authentication.getName();

        return usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                    "Usuário autenticado não encontrado"));
    }
    
}
