package com.autonomofinancas.mapper;

import org.springframework.stereotype.Component;

import com.autonomofinancas.dto.request.PlataformaRequest;
import com.autonomofinancas.dto.response.PlataformaResponse;
import com.autonomofinancas.entity.Plataforma;

@Component
public class PlataformaMapper {

    public Plataforma paraEntidade(PlataformaRequest request) {
        Plataforma plataforma = new Plataforma();

        plataforma.setNome(request.getNome());
        plataforma.setAtivo(true);

        return plataforma;
    }

    public void atualizarEntidade(PlataformaRequest request, Plataforma plataforma) {

        plataforma.setNome(request.getNome());
    }

    public PlataformaResponse paraResponse(Plataforma plataforma) {
        PlataformaResponse response = new PlataformaResponse();

        response.setId(plataforma.getId());
        response.setNome(plataforma.getNome());
        response.setAtivo(plataforma.getAtivo());
        response.setDataCriacao(plataforma.getDataCriacao());
        response.setDataAtualizacao(plataforma.getDataAtualizacao());

        return response;
    }
    
}
