package com.medsoft.labmedial.dtos.response;

import com.medsoft.labmedial.enums.NivelUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record UsuarioResponse(

        Long id,

        String nome,

        String genero,

        String cpf,

        String telefone,

        String email,

        String senha,

        NivelUsuario nivel,

        Boolean situacao

) {
}
