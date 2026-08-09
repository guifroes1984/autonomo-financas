package com.autonomofinancas.service.impl;

import java.time.OffsetDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autonomofinancas.dto.request.AlterarSenhaRequest;
import com.autonomofinancas.dto.request.AtualizarUsuarioRequest;
import com.autonomofinancas.dto.request.CriarUsuarioRequest;
import com.autonomofinancas.dto.response.UsuarioPerfilResponse;
import com.autonomofinancas.dto.response.UsuarioResponse;
import com.autonomofinancas.entity.Usuario;
import com.autonomofinancas.exception.EmailJaCadastradoException;
import com.autonomofinancas.exception.RecursoNaoEncontradoException;
import com.autonomofinancas.exception.SenhaInvalidaException;
import com.autonomofinancas.mapper.UsuarioMapper;
import com.autonomofinancas.repository.UsuarioRepository;
import com.autonomofinancas.service.UsuarioAutenticadoService;
import com.autonomofinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            UsuarioMapper usuarioMapper,
            PasswordEncoder passwordEncoder,
            UsuarioAutenticadoService usuarioAutenticadoService) {

        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
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

    @Override
    @Transactional(readOnly = true)
    public UsuarioPerfilResponse buscarPerfil() {

        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        Usuario usuario = usuarioRepository
                .findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        return usuarioMapper.paraPerfilResponse(usuario);

    }

    @Override
    @Transactional
    public UsuarioPerfilResponse atualizarPerfil(AtualizarUsuarioRequest request) {

        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        Usuario usuario = usuarioRepository
                .findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        usuarioRepository
                .findByEmailIgnoreCase(request.getEmail())
                .filter(outroUsuario -> !outroUsuario.getId().equals(usuarioId))
                .ifPresent(outroUsuario -> {
                    throw new EmailJaCadastradoException(
                            "Já exite um usuário cadastrado com este e-mail.");
                });

        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setDataAtualizacao(
                OffsetDateTime.now());

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        return usuarioMapper.paraPerfilResponse(usuarioAtualizado);

    }

    @Override
    public void alterarSenha(AlterarSenhaRequest request) {
        
        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        Usuario usuario = usuarioRepository
                .findById(usuarioId)
                .orElseThrow(() -> 
                    new RecursoNaoEncontradoException("Usuário não encontrado.")
            );

        if (!passwordEncoder.matches(request.getSenhaAtual(), usuario.getSenhaHash())) {
            throw new SenhaInvalidaException("A senha atual está incorreta.");
        }

        usuario.setSenhaHash(passwordEncoder.encode(request.getNovaSenha()));
        usuario.setDataAtualizacao(OffsetDateTime.now());

        usuarioRepository.save(usuario);

    }

}
