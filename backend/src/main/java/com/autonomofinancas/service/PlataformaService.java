package com.autonomofinancas.service;

import java.util.List;

import com.autonomofinancas.dto.request.PlataformaRequest;
import com.autonomofinancas.dto.response.PlataformaResponse;

public interface PlataformaService {

    PlataformaResponse criar(PlataformaRequest request);

    PlataformaResponse buscarPorId(Long id);

    List<PlataformaResponse> listar();

    PlataformaResponse atualizar(Long id, PlataformaRequest request);

    void desativar(Long id);
    
}
