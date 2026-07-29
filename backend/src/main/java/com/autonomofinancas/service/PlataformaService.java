package com.autonomofinancas.service;

import java.util.List;

import com.autonomofinancas.dto.request.AtualizarPlataformaRequest;
import com.autonomofinancas.dto.request.CriarPlataformaRequest;
import com.autonomofinancas.dto.response.PlataformaResponse;

public interface PlataformaService {

    PlataformaResponse criar(CriarPlataformaRequest request);

    PlataformaResponse buscarPorId(Long id);

    List<PlataformaResponse> listar();

    PlataformaResponse atualizar(Long id, AtualizarPlataformaRequest request);

    void desativar(Long id);
    
}
