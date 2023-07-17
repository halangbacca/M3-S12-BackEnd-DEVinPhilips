package com.medsoft.labmedial.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.medsoft.labmedial.enums.EstadoCivil;
import com.medsoft.labmedial.enums.NivelUsuario;
import com.medsoft.labmedial.models.Alergia;
import com.medsoft.labmedial.models.Precaucao;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.Collection;

public record UsuarioRequest(

        @NotBlank(message = "O preenchimento do nome do usuario é obrigatório!")
        @Size(min = 8, max = 64)
        String nome,

        @NotBlank(message = "O preenchimeto do gênero do usuario é obrigatório!")
        String genero,

        @NotBlank(message = "O preenchimento do CPF do usuario é obrigatório!")
        @CPF(message = "Formato de CPF inválido!")
        String cpf,

        @NotBlank(message = "O preenchimento do telefone do usuario é obrigatório!")
        String telefone,

        @NotBlank(message = "O preenchimento do e-mail do usuario é obrigatório!")
        @Email(message = "Formato de e-mail inválido!")
        String email,

        @NotBlank(message = "O preenchimento da senha do usuario é obrigatória!")
        String senha,

        //@NotBlank(message = "O nivel do usuário é obrigatório!")
        NivelUsuario nivel

) {
}
