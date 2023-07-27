package com.medsoft.labmedial.dtos.response;

import com.medsoft.labmedial.enums.TipoDieta;

import java.util.Date;

public record DietaResponse (
        Long id,
        String nomeDieta,
        Date dtaDieta,
        TipoDieta tipoDieta,
        String descricao,
        NomePaciente paciente,
        Boolean situacao
) { }
