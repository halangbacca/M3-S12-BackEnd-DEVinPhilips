package com.medsoft.labmedial.dtos.response;

import com.medsoft.labmedial.models.Paciente;

import java.util.Date;

public record ConsultaResponse(

        Long id,

        String motivo,

        Date dtaConsulta,

        String problema,

        String medicacao,

        String precausao,

        Paciente paciente

) {
}
