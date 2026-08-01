package com.autonomofinancas.mapper;

import org.springframework.stereotype.Component;

import com.autonomofinancas.dto.request.CategoriaRequest;
import com.autonomofinancas.dto.response.CategoriaResponse;
import com.autonomofinancas.entity.Categoria;
import com.autonomofinancas.entity.Usuario;

@Component
public class CategoriaMapper {

    public Categoria paraEntidade(
            CategoriaRequest request,
            Usuario usuario) {

        Categoria categoria = new Categoria();

        categoria.setNome(request.getNome().trim());
        categoria.setTipo(request.getTipo());
        categoria.setCor(request.getCor());
        categoria.setAtiva(true);
        categoria.setUsuario(usuario);

        return categoria;
    }

    public void atualizarEntidade(
            CategoriaRequest request,
            Categoria categoria) {

        categoria.setNome(request.getNome().trim());
        categoria.setTipo(request.getTipo());
        categoria.setCor(request.getCor());
    }

    public CategoriaResponse paraResponse(Categoria categoria) {
        CategoriaResponse response = new CategoriaResponse();

        response.setId(categoria.getId());
        response.setNome(categoria.getNome());
        response.setTipo(categoria.getTipo());
        response.setCor(categoria.getCor());
        response.setAtiva(categoria.getAtiva());
        response.setDataCriacao(categoria.getDataCriacao());
        response.setDataAtualizacao(categoria.getDataAtualizacao());

        return response;
    }

}
