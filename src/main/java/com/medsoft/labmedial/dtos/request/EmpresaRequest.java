package com.medsoft.labmedial.dtos.request;

import jakarta.validation.constraints.NotNull;

public record EmpresaRequest(
        @NotNull(message = "O nome da empresa é obrigatório!")
        String nome,
        String slogan,
        @NotNull(message = "A escolha da palheta de cores é obrigatória!")
        String palhetaDeCores,
        @NotNull(message = "A URL da logotipo é obrigatória!")
        String logotipo
) {
}
