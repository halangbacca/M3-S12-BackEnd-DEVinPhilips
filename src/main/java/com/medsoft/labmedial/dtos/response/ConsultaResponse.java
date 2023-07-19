package com.medsoft.labmedial.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record ConsultaResponse(

        Long id,

        NomePaciente paciente,

        String motivo,

        @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
        Date dtaConsulta,

        String problema,

        String medicacao,

        String precaucao,

        Boolean situacao

) {
}
