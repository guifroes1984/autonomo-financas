package com.autonomofinancas.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.autonomofinancas.dto.request.LancamentoFiltroRequest;
import com.autonomofinancas.dto.request.LancamentoRequest;
import com.autonomofinancas.dto.response.LancamentoResponse;

public interface LancamentoService {

    LancamentoResponse criar(LancamentoRequest request);

    LancamentoResponse buscarPorId(Long id);

    Page<LancamentoResponse> listar(LancamentoFiltroRequest filtro, Pageable pageable);

    LancamentoResponse atualizar(Long id, LancamentoRequest request);

    void excluir(Long id);
    
}
