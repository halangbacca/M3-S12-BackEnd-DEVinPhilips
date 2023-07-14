package com.medsoft.labmedial.records.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.medsoft.labmedial.models.Endereco;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record PacienteRequest(
        @NotNull(message = "O ID do tipo de usuário não deve ser nulo!")
        Long tipoDeUsuarioId,

        @NotBlank(message = "O preenchimento do nome do paciente é obrigatório!")
        @Size(min = 8, max = 64)
        String nomeCompleto,

        @NotBlank(message = "O preenchimeto do gênero do paciente é obrigatório!")
        String genero,

        @NotNull(message = "O preenchimento da data de nascimento do paciente é obrigatória!")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataDeNascimento,

        @NotBlank(message = "O preenchimento do CPF do paciente é obrigatório!")
        @CPF(message = "Formato de CPF inválido!")
        String cpf,

        @NotBlank(message = "O preenchimento do RG do paciente é obrigatório!")
        @Size(max = 20, message = "O RG deve possuir no máximo 20 caracteres!")
        String rg,

        @NotBlank(message = "O preenchimento do estado civil do paciente é obrigatório!")
        String estadoCivil,

        @NotBlank(message = "O preenchimento do telefone do paciente é obrigatório!")
        String telefone,

        @NotBlank(message = "O preenchimento do e-mail do paciente é obrigatório!")
        @Email(message = "Formato de e-mail inválido!")
        String email,

        @NotBlank(message = "O preenchimento da naturalidade do paciente é obrigatória!")
        @Size(min = 8, max = 64, message = "A naturalidade deve conter entre 8 e 64 caracteres!")
        String naturalidade,

        @NotBlank(message = "O contato de emergência do paciente não deve estar em branco!")
        String contatoDeEmergencia,

        String listaDeAlergias,

        String listaDeCuidadosEspecificos,

        String convenio,

        String numeroDoConvenio,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate validadeDoConvenio,

        Endereco endereco
) {
}
