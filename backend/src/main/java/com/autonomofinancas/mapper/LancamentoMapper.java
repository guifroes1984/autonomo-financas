package com.autonomofinancas.mapper;

import org.springframework.stereotype.Component;

import com.autonomofinancas.dto.request.LancamentoRequest;
import com.autonomofinancas.dto.response.CategoriaSimplificadaResponse;
import com.autonomofinancas.dto.response.LancamentoResponse;
import com.autonomofinancas.dto.response.PlataformaSimplificadaResponse;
import com.autonomofinancas.entity.Categoria;
import com.autonomofinancas.entity.Lancamento;
import com.autonomofinancas.entity.Plataforma;

@Component
public class LancamentoMapper {

    public Lancamento paraEntidade(LancamentoRequest request) {

        Lancamento lancamento = new Lancamento();

        lancamento.setTipo(request.getTipo());
        lancamento.setDescricao(request.getDescricao());
        lancamento.setValor(request.getValor());
        lancamento.setDataLancamento(request.getDataLancamento());
        lancamento.setObservacao(request.getObservacao());

        return lancamento;
    }

    public void atualizarEntidade(
            LancamentoRequest request,
            Lancamento lancamento) {

        lancamento.setTipo(request.getTipo());
        lancamento.setDescricao(request.getDescricao());
        lancamento.setValor(request.getValor());
        lancamento.setDataLancamento(request.getDataLancamento());
        lancamento.setObservacao(request.getObservacao());

    }

    public LancamentoResponse paraResponse(Lancamento lancamento) {

        LancamentoResponse response = new LancamentoResponse();

        response.setId(lancamento.getId());
        response.setTipo(lancamento.getTipo());
        response.setDescricao(lancamento.getDescricao());
        response.setValor(lancamento.getValor());
        response.setDataLancamento(lancamento.getDataLancamento());
        response.setObservacao(lancamento.getObservacao());
        response.setDataCriacao(lancamento.getDataCriacao());
        response.setDataAtualizacao(lancamento.getDataAtualizacao());

        response.setCategoria(
                paraCategoriaSimplificada(lancamento.getCategoria()));

        if (lancamento.getPlataforma() != null) {
            response.setPlataforma(
                    paraPlataformaSimplificada(lancamento.getPlataforma()));
        }

        return response;
    }

    private CategoriaSimplificadaResponse paraCategoriaSimplificada(
            Categoria categoria) {

        CategoriaSimplificadaResponse response = new CategoriaSimplificadaResponse();

        response.setId(categoria.getId());
        response.setNome(categoria.getNome());

        return response;
    }

    private PlataformaSimplificadaResponse paraPlataformaSimplificada(
            Plataforma plataforma) {

        PlataformaSimplificadaResponse response = new PlataformaSimplificadaResponse();

        response.setId(plataforma.getId());
        response.setNome(plataforma.getNome());

        return response;
    }

}
