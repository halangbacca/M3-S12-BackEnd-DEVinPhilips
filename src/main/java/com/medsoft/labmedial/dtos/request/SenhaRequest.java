package com.medsoft.labmedial.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SenhaRequest(

        @NotNull(message = "O Id precisa ser um número válido!")
        Long Id,

        @NotBlank(message = "O preenchimento do e-mail do usuário é obrigatório!")
        @Email(message = "Formato de e-mail inválido!")
        String email,

        @NotBlank(message = "O preenchimento da senha do usuário é obrigatória!")
        String senha

    ){

}
