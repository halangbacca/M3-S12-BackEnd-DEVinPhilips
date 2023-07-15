package com.medsoft.labmedial.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoRequest(
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
