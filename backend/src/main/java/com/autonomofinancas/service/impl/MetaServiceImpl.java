package com.autonomofinancas.service.impl;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autonomofinancas.dto.request.MetaRequest;
import com.autonomofinancas.dto.response.MetaResponse;
import com.autonomofinancas.entity.Meta;
import com.autonomofinancas.entity.Usuario;
import com.autonomofinancas.exception.RecursoNaoEncontradoException;
import com.autonomofinancas.exception.RegraDeNegocioException;
import com.autonomofinancas.mapper.MetaMapper;
import com.autonomofinancas.repository.MetaRepository;
import com.autonomofinancas.repository.UsuarioRepository;
import com.autonomofinancas.security.MetaService;
import com.autonomofinancas.service.UsuarioAutenticadoService;

@Service
public class MetaServiceImpl implements MetaService {

    private final MetaRepository metaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final MetaMapper metaMapper;

    public MetaServiceImpl(
            MetaRepository metaRepository,
            UsuarioRepository usuarioRepository,
            UsuarioAutenticadoService usuarioAutenticadoService,
            MetaMapper metaMapper) {

        this.metaRepository = metaRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.metaMapper = metaMapper;
    }

    @Override
    @Transactional
    public MetaResponse criar(MetaRequest request) {

        Usuario usuario = obterUsuarioAutenticado();

        if (metaRepository.existsByUsuarioId(usuario.getId())) {
            throw new RegraDeNegocioException("O usuário já possui uma meta cadastrada.");
        }

        Meta meta = new Meta();

        meta.setUsuario(usuario);
        meta.setMetaDiaria(request.getMetaDiaria());
        meta.setMetaMensal(request.getMetaMensal());

        OffsetDateTime agora = OffsetDateTime.now();

        meta.setDataCriacao(agora);
        meta.setDataAtualizacao(agora);

        Meta metaSalva = metaRepository.save(meta);

        return metaMapper.paraResponse(metaSalva);
    }

    @Override
    @Transactional(readOnly = true)
    public MetaResponse buscar() {

        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        Meta meta = buscarMetaDoUsuario(usuarioId);

        return metaMapper.paraResponse(meta);
    }

    @Override
    @Transactional
    public MetaResponse atualizar(MetaRequest request) {

        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        Meta meta = buscarMetaDoUsuario(usuarioId);

        meta.setMetaDiaria(request.getMetaDiaria());
        meta.setMetaMensal(request.getMetaMensal());
        meta.setDataAtualizacao(OffsetDateTime.now());

        Meta metaAtualizada = metaRepository.save(meta);

        return metaMapper.paraResponse(metaAtualizada);
    }

    private Meta buscarMetaDoUsuario(Long usuarioId) {

        return metaRepository
                .findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada."));

    }

    private Usuario obterUsuarioAutenticado() {
        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        return usuarioRepository
                .findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
    }

}
