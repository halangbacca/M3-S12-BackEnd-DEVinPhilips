package com.medsoft.labmedial.dtos.response;

import com.medsoft.labmedial.enums.TipoDieta;

import java.time.LocalDate;

public record DietaResponse (
        Long id,
        String nomeDieta,
        LocalDate dtaDieta,
        TipoDieta tipoDieta,
        String descricao,
        NomePaciente paciente,
        Boolean situacao
) { }
