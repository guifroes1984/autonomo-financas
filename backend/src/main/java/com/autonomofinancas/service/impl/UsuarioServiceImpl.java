package com.autonomofinancas.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.autonomofinancas.dto.request.CriarUsuarioRequest;
import com.autonomofinancas.dto.request.UsuarioResponse;
import com.autonomofinancas.entity.Usuario;
import com.autonomofinancas.exception.EmailJaCadastradoException;
import com.autonomofinancas.mapper.UsuarioMapper;
import com.autonomofinancas.repository.UsuarioRepository;
import com.autonomofinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            UsuarioMapper usuarioMapper,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioResponse criar(CriarUsuarioRequest request) {

        usuarioRepository.findByEmailIgnoreCase(request.getEmail())
                .ifPresent(usuario -> {
                    throw new EmailJaCadastradoException(request.getEmail());
                });

        String senhaHash = passwordEncoder.encode(request.getSenha());

        Usuario usuario = usuarioMapper.paraEntidade(request, senhaHash);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return usuarioMapper.paraResponse(usuarioSalvo);

    }

}
