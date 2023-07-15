package com.medsoft.labmedial.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.medsoft.labmedial.enums.EstadoCivil;
import com.medsoft.labmedial.models.Endereco;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record PacienteRequest(

        @NotBlank(message = "O preenchimento do nome do paciente é obrigatório!")
        @Size(min = 8, max = 64)
        String nome,

        @NotBlank(message = "O preenchimeto do gênero do paciente é obrigatório!")
        String genero,

        @NotNull(message = "O preenchimento da data de nascimento do paciente é obrigatória!")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dtaNascimento,

        @NotBlank(message = "O preenchimento do CPF do paciente é obrigatório!")
        @CPF(message = "Formato de CPF inválido!")
        String cpf,

        @NotBlank(message = "O preenchimento do RG do paciente é obrigatório!")
        @Size(max = 20, message = "O RG deve possuir no máximo 20 caracteres!")
        String rg,

        //@NotBlank(message = "O preenchimento do estado civil do paciente é obrigatório!")
        EstadoCivil estadoCivil,

        @NotBlank(message = "O preenchimento do telefone do paciente é obrigatório!")
        String telefone,

        @NotBlank(message = "O preenchimento do e-mail do paciente é obrigatório!")
        @Email(message = "Formato de e-mail inválido!")
        String email,

        @NotBlank(message = "O preenchimento da naturalidade do paciente é obrigatória!")
        @Size(min = 8, max = 64, message = "A naturalidade deve conter entre 8 e 64 caracteres!")
        String naturalidade,

        @NotBlank(message = "O contato de emergência do paciente não deve estar em branco!")
        String telEmergencia,

        String convenio,

        String nroConvenio,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate validadeConvenio,

        //Endereço
        @NotBlank(message = "O preenchimento do CEP do paciente é obrigatório!")
        String cep,

        @NotBlank(message = "O preenchimento da cidade do paciente é obrigatória!")
        String cidade,

        @NotBlank(message = "O preenchimento do estado de residência do paciente é obrigatório!")
        String estado,

        @NotBlank(message = "O preenchimento do logradouro do paciente é obrigatório!")
        String logradouro,

        @NotNull(message = "O preenchimento do número da residência do paciente é obrigatório!")
        String numero,

        String complemento,

        @NotBlank(message = "O preenchimento do bairro de residência do paciente é obrigatório!")
        String bairro,

        String referencia
) {
}
