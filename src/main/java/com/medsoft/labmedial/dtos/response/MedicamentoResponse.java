package com.medsoft.labmedial.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record MedicamentoResponse(

        Long id,

        String descricao,

        @JsonFormat(pattern = "dd/MM/yyyy")
        Date dtaMedicamento,

        String tipo,

        String unidade,

        String observacao

) {
}
