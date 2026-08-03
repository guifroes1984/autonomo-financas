package com.autonomofinancas.security;

import java.util.List;

import com.autonomofinancas.dto.request.LancamentoRequest;
import com.autonomofinancas.dto.response.LancamentoResponse;

public interface LancamentoService {

    LancamentoResponse criar(LancamentoRequest request);

    LancamentoResponse buscarPorId(Long id);

    List<LancamentoResponse> listar();

    LancamentoResponse atualizar(Long id, LancamentoRequest request);

    void excluir(Long id);
    
}
