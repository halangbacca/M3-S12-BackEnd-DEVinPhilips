package com.medsoft.labmedial.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record MedicamentoResponse(

        Long id,

        NomePaciente paciente,

        String descricao,

        Date dtaMedicamento,

        String tipo,

        String unidade,

        long quantidade,

        String observacao,

        Boolean situacao

) {
}
