package com.autonomofinancas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autonomofinancas.dto.request.LancamentoRequest;
import com.autonomofinancas.dto.response.LancamentoResponse;
import com.autonomofinancas.entity.Categoria;
import com.autonomofinancas.entity.Lancamento;
import com.autonomofinancas.entity.Plataforma;
import com.autonomofinancas.entity.Usuario;
import com.autonomofinancas.exception.RecursoNaoEncontradoException;
import com.autonomofinancas.exception.RegraDeNegocioException;
import com.autonomofinancas.mapper.LancamentoMapper;
import com.autonomofinancas.repository.CategoriaRepository;
import com.autonomofinancas.repository.LancamentoRepository;
import com.autonomofinancas.repository.PlataformaRepository;
import com.autonomofinancas.repository.UsuarioRepository;
import com.autonomofinancas.security.LancamentoService;
import com.autonomofinancas.service.UsuarioAutenticadoService;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private final LancamentoRepository lancamentoRepository;
    private final CategoriaRepository categoriaRepository;
    private final PlataformaRepository plataformaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LancamentoMapper lancamentoMapper;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    public LancamentoServiceImpl(
            LancamentoRepository lancamentoRepository,
            CategoriaRepository categoriaRepository,
            PlataformaRepository plataformaRepository,
            UsuarioRepository usuarioRepository,
            LancamentoMapper lancamentoMapper,
            UsuarioAutenticadoService usuarioAutenticadoService) {

        this.lancamentoRepository = lancamentoRepository;
        this.categoriaRepository = categoriaRepository;
        this.plataformaRepository = plataformaRepository;
        this.usuarioRepository = usuarioRepository;
        this.lancamentoMapper = lancamentoMapper;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    @Override
    @Transactional
    public LancamentoResponse criar(LancamentoRequest request) {
        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        Usuario usuario = obterUsuario(usuarioId);

        Categoria categoria = obterCategoria(request.getCategoriaId(), usuarioId);

        validarCategoria(categoria, request);

        Plataforma plataforma = obterPlataforma(request.getPlataformaId(), usuarioId);

        Lancamento lancamento = lancamentoMapper.paraEntidade(request);

        lancamento.setDescricao(normalizarTexto(request.getDescricao()));
        lancamento.setObservacao(normalizarTextoOpcional(request.getObservacao()));
        lancamento.setUsuario(usuario);
        lancamento.setCategoria(categoria);
        lancamento.setPlataforma(plataforma);

        Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);

        return lancamentoMapper.paraResponse(lancamentoSalvo);
    }

    @Override
    @Transactional(readOnly = true)
    public LancamentoResponse buscarPorId(Long id) {
        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        Lancamento lancamento = buscarLancamentoDoUsuario(id, usuarioId);

        return lancamentoMapper.paraResponse(lancamento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LancamentoResponse> listar() {
        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        return lancamentoRepository
                .findAllByUsuarioIdOrderByDataLancamentoDescIdDesc(
                        usuarioId)
                .stream()
                .map(lancamentoMapper::paraResponse)
                .toList();
    }

    @Override
    @Transactional
    public LancamentoResponse atualizar(Long id, LancamentoRequest request) {
        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        Lancamento lancamento = buscarLancamentoDoUsuario(id, usuarioId);

        Categoria categoria = obterCategoria(request.getCategoriaId(), usuarioId);

        validarCategoria(categoria, request);

        Plataforma plataforma = obterPlataforma(request.getPlataformaId(), usuarioId);

        lancamentoMapper.atualizarEntidade(request, lancamento);

        lancamento.setDescricao(normalizarTexto(request.getDescricao()));

        lancamento.setObservacao(normalizarTextoOpcional(request.getObservacao()));

        lancamento.setCategoria(categoria);
        lancamento.setPlataforma(plataforma);

        Lancamento lancamentoAtualizado = lancamentoRepository.save(lancamento);

        return lancamentoMapper.paraResponse(lancamentoAtualizado);
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        Lancamento lancamento = buscarLancamentoDoUsuario(id, usuarioId);

        lancamentoRepository.delete(lancamento);
    }

    private Usuario obterUsuario(Long usuarioId) {
        return usuarioRepository
                .findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuário autenticado não encontrado."));
    }

    private Categoria obterCategoria(
            Long categoriaId,
            Long usuarioId) {

        return categoriaRepository
                .findByIdAndUsuarioId(categoriaId, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Categoria não encontrada."));
    }

    private Plataforma obterPlataforma(
            Long plataformaId,
            Long usuarioId) {

        if (plataformaId == null) {
            return null;
        }

        Plataforma plataforma = plataformaRepository
                .findByIdAndUsuarioId(plataformaId, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Plataforma não encontrada."));

        if (Boolean.FALSE.equals(plataforma.getAtivo())) {
            throw new RegraDeNegocioException(
                    "A plataforma informada está desativada.");
        }

        return plataforma;
    }

    private Lancamento buscarLancamentoDoUsuario(
            Long lancamentoId,
            Long usuarioId) {

        return lancamentoRepository
                .findByIdAndUsuarioId(lancamentoId, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Lançamento não encontrado."));
    }

    private void validarCategoria(
            Categoria categoria,
            LancamentoRequest request) {

        if (Boolean.FALSE.equals(categoria.getAtiva())) {
            throw new RegraDeNegocioException(
                    "A categoria informada está desativada.");
        }

        boolean tiposDiferentes = !categoria.getTipo()
                .name()
                .equals(request.getTipo().name());

        if (tiposDiferentes) {
            throw new RegraDeNegocioException(
                    "O tipo da categoria deve ser igual ao tipo do lançamento.");
        }
    }

    private String normalizarTexto(String texto) {
        return texto
                .trim()
                .replaceAll("\\s+", " ");
    }

    private String normalizarTextoOpcional(String texto) {
        if (texto == null || texto.isBlank()) {
            return null;
        }

        return texto
                .trim()
                .replaceAll("\\s+", " ");
    }

}
