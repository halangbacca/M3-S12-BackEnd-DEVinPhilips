package com.medsoft.labmedial.dtos.response;

public record EmpresaResponse(
        Long id,
        String nome,
        String slogan,
        String palhetaDeCores,
        String logotipo,
        Boolean situacao
) {
}
