package com.autonomofinancas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.autonomofinancas.dto.request.AtualizarPlataformaRequest;
import com.autonomofinancas.dto.request.CriarPlataformaRequest;
import com.autonomofinancas.dto.response.PlataformaResponse;
import com.autonomofinancas.entity.Plataforma;
import com.autonomofinancas.entity.Usuario;
import com.autonomofinancas.exception.RecursoNaoEncontradoException;
import com.autonomofinancas.exception.RegraDeNegocioException;
import com.autonomofinancas.mapper.PlataformaMapper;
import com.autonomofinancas.repository.PlataformaRepository;
import com.autonomofinancas.service.PlataformaService;
import com.autonomofinancas.service.UsuarioAutenticadoService;

import org.springframework.transaction.annotation.Transactional;

@Service
public class PlataformaServiceImpl implements PlataformaService {

    private final PlataformaRepository plataformaRepository;
    private final PlataformaMapper plataformaMapper;
    private final UsuarioAutenticadoService usuarioAutenticadoService;

    public PlataformaServiceImpl(
            PlataformaRepository plataformaRepository,
            PlataformaMapper plataformaMapper,
            UsuarioAutenticadoService usuarioAutenticadoService) {

        this.plataformaRepository = plataformaRepository;
        this.plataformaMapper = plataformaMapper;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
    }

    @Override
    @Transactional
    public PlataformaResponse criar(CriarPlataformaRequest request) {
        Usuario usuario = usuarioAutenticadoService.obterUsuario();
        String nomeNormalizado = normalizarNome(request.getNome());

        validarNomeDuplicado(usuario.getId(), nomeNormalizado);

        Plataforma plataforma =
                plataformaMapper.paraEntidade(request);

        plataforma.setNome(nomeNormalizado);
        plataforma.setUsuario(usuario);

        Plataforma plataformaSalva =
                plataformaRepository.save(plataforma);

        return plataformaMapper.paraResponse(plataformaSalva);
    }

    @Override
    @Transactional(readOnly = true)
    public PlataformaResponse buscarPorId(Long id) {
        Usuario usuario = usuarioAutenticadoService.obterUsuario();

        Plataforma plataforma = buscarPlataformaDoUsuario(id, usuario.getId());

        return plataformaMapper.paraResponse(plataforma);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlataformaResponse> listar() {
        Usuario usuario = usuarioAutenticadoService.obterUsuario();

        return plataformaRepository
                .findAllByUsuarioId(usuario.getId())
                .stream()
                .map(plataformaMapper::paraResponse)
                .toList();
    }

    @Override
    @Transactional
    public PlataformaResponse atualizar(Long id, AtualizarPlataformaRequest request) {
        Usuario usuario = usuarioAutenticadoService.obterUsuario();

        Plataforma plataforma = buscarPlataformaDoUsuario(id, usuario.getId());

        String nomeNormalizado = normalizarNome(request.getNome());

        boolean nomeFoiAlterado = !plataforma.getNome().equalsIgnoreCase(nomeNormalizado);

        if (nomeFoiAlterado) {
            validarNomeDuplicado(usuario.getId(), nomeNormalizado);
        }

        plataformaMapper.atualizarEntidade(request, plataforma);
        plataforma.setNome(nomeNormalizado);

        Plataforma plataformaAtualizada = plataformaRepository.save(plataforma);

        return plataformaMapper.paraResponse(plataformaAtualizada);
    }

    @Override
    @Transactional
    public void desativar(Long id) {
        Usuario usuario = usuarioAutenticadoService.obterUsuario();

        Plataforma plataforma = buscarPlataformaDoUsuario(id, usuario.getId());

        if (Boolean.FALSE.equals(plataforma.getAtivo())) {
            throw new RegraDeNegocioException("A plataforma já está desativada.");
        }

        plataforma.setAtivo(false);
        plataformaRepository.save(plataforma);
    }

    private Plataforma buscarPlataformaDoUsuario(Long plataformaId, Long usuarioId) {
        return plataformaRepository
                .findByIdAndUsuarioId(plataformaId, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                    "Plataforma não encontrada."));
    }

    private void validarNomeDuplicado(Long usuarioId, String nome) {
        boolean exite = plataformaRepository.existsByUsuarioIdAndNomeIgnoreCase(usuarioId, nome);

        if (exite) {
            throw new RegraDeNegocioException("Já existe uma plataforma com esse nome.");
        }
    }

    private String normalizarNome(String nome) {
        return nome.trim();
    }
    
}
