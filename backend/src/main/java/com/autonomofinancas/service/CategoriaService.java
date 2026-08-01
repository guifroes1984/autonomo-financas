package com.autonomofinancas.service;

import java.util.List;

import com.autonomofinancas.dto.request.CategoriaRequest;
import com.autonomofinancas.dto.response.CategoriaResponse;

public interface CategoriaService {

    CategoriaResponse criar(CategoriaRequest request);

    CategoriaResponse buscarPorId(Long id);

    List<CategoriaResponse> listar();

    CategoriaResponse atualizar(Long id, CategoriaRequest request);

    void desativar(Long id);
    
}
