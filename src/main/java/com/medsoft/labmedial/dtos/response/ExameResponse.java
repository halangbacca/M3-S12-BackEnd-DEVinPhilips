package com.medsoft.labmedial.dtos.response;

import com.medsoft.labmedial.models.Paciente;

import java.util.Date;

public record ExameResponse(

        Long id,

        String descricao,

        Date dtaExame,

        String tipo,

        String documento,

        String resultado,

        Paciente paciente,
        Boolean situacao

) {
}
