package com.medsoft.labmedial.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(

        @Email(message = "Email Inválido")
        @NotEmpty(message = "Email Obrigatório")
        String email,

        @NotEmpty(message = "Senha do usuário obrigatório")
        String senha
) {
}
