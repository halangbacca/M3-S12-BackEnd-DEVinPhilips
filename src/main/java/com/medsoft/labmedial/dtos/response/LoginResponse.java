package com.medsoft.labmedial.dtos.response;

import com.medsoft.labmedial.enums.NivelUsuario;
import com.medsoft.labmedial.models.Usuario;

public record LoginResponse(

        String nome,
        String email,
        NivelUsuario nivel,
        String token

) {

    public LoginResponse(Usuario usuario, String token){
        this(usuario.getNome(), usuario.getEmail(), usuario.getNivel(), token);
    }
}