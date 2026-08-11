package com.autonomofinancas.service.impl;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autonomofinancas.dto.request.EsqueciSenhaRequest;
import com.autonomofinancas.dto.request.RedefinirSenhaRequest;
import com.autonomofinancas.entity.TokenRecuperacaoSenha;
import com.autonomofinancas.entity.Usuario;
import com.autonomofinancas.exception.RecursoNaoEncontradoException;
import com.autonomofinancas.exception.TokenRecuperacaoExpiradoException;
import com.autonomofinancas.exception.TokenRecuperacaoInvalidoException;
import com.autonomofinancas.repository.TokenRecuperacaoSenhaRepository;
import com.autonomofinancas.repository.UsuarioRepository;
import com.autonomofinancas.service.EmailService;
import com.autonomofinancas.service.RecuperacaoSenhaService;

@Service
public class RecuperacaoSenhaServiceImpl implements RecuperacaoSenhaService {

    private final UsuarioRepository usuarioRepository;
    private final TokenRecuperacaoSenhaRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public RecuperacaoSenhaServiceImpl(

            UsuarioRepository usuarioRepository,
            TokenRecuperacaoSenhaRepository tokenRepository,
            PasswordEncoder passwordEncoder, 
            EmailService emailService) {

        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public void solicitarRecuperacaoSenha(EsqueciSenhaRequest request) {

        Usuario usuario = usuarioRepository
                .findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

        List<TokenRecuperacaoSenha> tokensAtivos = tokenRepository
                .findByUsuarioIdAndUtilizadoFalse(usuario.getId());

        tokensAtivos.forEach(token -> token.setUtilizado(true));

        tokenRepository.saveAll(tokensAtivos);

        TokenRecuperacaoSenha novoToken = new TokenRecuperacaoSenha();

        OffsetDateTime agora = OffsetDateTime.now();

        novoToken.setUsuario(usuario);
        novoToken.setToken(gerarToken());
        novoToken.setExpiracao(agora.plusMinutes(30));
        novoToken.setUtilizado(false);
        novoToken.setDataCriacao(agora);

        tokenRepository.save(novoToken);

        emailService.enviarRecuperacaoSenha(
                usuario, 
                novoToken.getToken());

    }

    @Override
    @Transactional
    public void redefinirSenha(
            RedefinirSenhaRequest request) {

        TokenRecuperacaoSenha token = buscarToken(request.getToken());

        validarToken(token);

        atualizarSenha(
                token.getUsuario(),
                request.getNovaSenha());

        token.setUtilizado(true);

        tokenRepository.save(token);
    }

    private TokenRecuperacaoSenha buscarToken(
            String valorToken) {

        return tokenRepository
                .findByToken(valorToken)
                .orElseThrow(() -> new TokenRecuperacaoInvalidoException(
                        "Token de recuperação inválido."));
    }

    private void validarToken(
            TokenRecuperacaoSenha token) {

        if (token.expirado()) {
            throw new TokenRecuperacaoExpiradoException(
                    "O token de recuperação expirou.");
        }

        if (!token.disponivel()) {
            throw new TokenRecuperacaoInvalidoException(
                    "O token de recuperação já foi utilizado.");
        }
    }

    private void atualizarSenha(
            Usuario usuario,
            String novaSenha) {

        usuario.setSenhaHash(
                passwordEncoder.encode(novaSenha));

        usuario.setDataAtualizacao(
                OffsetDateTime.now());

        usuarioRepository.save(usuario);
    }

    private String gerarToken() {
        return UUID.randomUUID().toString();
    }

}
