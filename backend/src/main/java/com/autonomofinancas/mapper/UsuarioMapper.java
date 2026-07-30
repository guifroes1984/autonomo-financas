package com.autonomofinancas.mapper;

import org.springframework.stereotype.Component;

import com.autonomofinancas.dto.request.CriarUsuarioRequest;
import com.autonomofinancas.dto.request.UsuarioResponse;
import com.autonomofinancas.entity.Usuario;

@Component
public class UsuarioMapper {

    public Usuario paraEntidade(CriarUsuarioRequest request, String senhaHash) {

        Usuario usuario = new Usuario();

        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenhaHash(senhaHash);
        usuario.setAtivo(true);

        return usuario;
    }

    public UsuarioResponse paraResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getAtivo(),
                usuario.getDataCriacao());
    }

}
