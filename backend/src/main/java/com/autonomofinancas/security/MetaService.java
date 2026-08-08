package com.autonomofinancas.security;

import com.autonomofinancas.dto.request.MetaRequest;
import com.autonomofinancas.dto.response.MetaResponse;

public interface MetaService {

    MetaResponse criar(MetaRequest request);

    MetaResponse buscar();

    MetaResponse atualizar(MetaRequest request);
    
}
