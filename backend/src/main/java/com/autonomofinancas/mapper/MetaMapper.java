package com.autonomofinancas.mapper;

import org.springframework.stereotype.Component;

import com.autonomofinancas.dto.response.MetaResponse;
import com.autonomofinancas.entity.Meta;

@Component
public class MetaMapper {

    public MetaResponse paraResponse(Meta meta) {

        MetaResponse response = new MetaResponse();

        response.setId(meta.getId());
        response.setMetaDiaria(meta.getMetaDiaria());
        response.setMetaMensal(meta.getMetaMensal());
        response.setDataCriacao(meta.getDataCriacao());
        response.setDataAtualizacao(meta.getDataAtualizacao());

        return response;
    }

}
