package com.autonomofinancas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autonomofinancas.dto.request.CategoriaRequest;
import com.autonomofinancas.dto.response.CategoriaResponse;
import com.autonomofinancas.entity.Categoria;
import com.autonomofinancas.entity.Usuario;
import com.autonomofinancas.entity.enums.TipoCategoria;
import com.autonomofinancas.exception.RecursoNaoEncontradoException;
import com.autonomofinancas.exception.RegraDeNegocioException;
import com.autonomofinancas.mapper.CategoriaMapper;
import com.autonomofinancas.repository.CategoriaRepository;
import com.autonomofinancas.repository.UsuarioRepository;
import com.autonomofinancas.service.CategoriaService;
import com.autonomofinancas.service.UsuarioAutenticadoService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    public CategoriaServiceImpl(
            CategoriaRepository categoriaRepository,
            CategoriaMapper categoriaMapper,
            UsuarioRepository usuarioRepository,
            UsuarioAutenticadoService usuarioAutenticadoService) {

        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
        this.usuarioRepository = usuarioRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    @Override
    @Transactional
    public CategoriaResponse criar(CategoriaRequest request) {
        Usuario usuario = obterUsuarioAutenticado();
        String nomeNormalizado = normalizarNome(request.getNome());

        validarDuplicidade(
                usuario.getId(),
                nomeNormalizado,
                request.getTipo());

        Categoria categoria = categoriaMapper.paraEntidade(request, usuario);

        categoria.setNome(nomeNormalizado);

        Categoria categoriaSalva = categoriaRepository.save(categoria);

        return categoriaMapper.paraResponse(categoriaSalva);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponse buscarPorId(Long id) {
        Long UsuarioId = usuarioAutenticadoService.obterUsuarioId();

        Categoria categoria = buscarCategoriaDoUsuario(id, UsuarioId);

        return categoriaMapper.paraResponse(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponse> listar() {
        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        return categoriaRepository
                .findAllByUsuarioIdOrderByNomeAsc(usuarioId)
                .stream()
                .map(categoriaMapper::paraResponse)
                .toList();

    }

    @Override
    @Transactional
    public CategoriaResponse atualizar(Long id, CategoriaRequest request) {
        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        Categoria categoria = buscarCategoriaDoUsuario(id, usuarioId);

        String nomeNormalizado = normalizarNome(request.getNome());

        boolean nomeFoiAlterado = !categoria.getNome().equalsIgnoreCase(nomeNormalizado);

        boolean tipoFoiAlterado = categoria.getTipo() != request.getTipo();

        if (nomeFoiAlterado || tipoFoiAlterado) {
            validarDuplicidade(
                    usuarioId,
                    nomeNormalizado,
                    request.getTipo());
        }

        categoriaMapper.atualizarEntidade(request, categoria);
        categoria.setNome(nomeNormalizado);

        Categoria categoriaAtualizada = categoriaRepository.save(categoria);

        return categoriaMapper.paraResponse(categoriaAtualizada);

    }

    @Override
    @Transactional
    public void desativar(Long id) {
        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        Categoria categoria = buscarCategoriaDoUsuario(id, usuarioId);

        if (Boolean.FALSE.equals(categoria.getAtiva())) {
            throw new RegraDeNegocioException("A categoria já está desativada");
        }

        categoria.setAtiva(false);
        categoriaRepository.save(categoria);

    }

    @Override
    @Transactional
    public void ativar(Long id) {
        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        Categoria categoria = buscarCategoriaDoUsuario(id, usuarioId);

        if (Boolean.TRUE.equals(categoria.getAtiva())) {
            throw new RegraDeNegocioException("A categoria já está ativa");
        }

        categoria.setAtiva(true);
        categoriaRepository.save(categoria);

    }

    private Usuario obterUsuarioAutenticado() {
        Long usuarioId = usuarioAutenticadoService.obterUsuarioId();

        return usuarioRepository
                .findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuário autenticado não encontrado."));
    }

    private Categoria buscarCategoriaDoUsuario(
            Long categoriaId,
            Long usuarioId) {

        return categoriaRepository
                .findByIdAndUsuarioId(categoriaId, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Categoria não encontrada."));
    }

    private void validarDuplicidade(
            Long usuarioId,
            String nome,
            TipoCategoria tipo) {

        boolean existe = categoriaRepository
                .existsByUsuarioIdAndNomeIgnoreCaseAndTipo(
                        usuarioId,
                        nome,
                        tipo);

        if (existe) {
            throw new RegraDeNegocioException(
                    "Já existe uma categoria com esse nome e tipo.");
        }
    }

    private String normalizarNome(String nome) {
        return nome
                .trim()
                .replaceAll("\\s+", " ");
    }

}
