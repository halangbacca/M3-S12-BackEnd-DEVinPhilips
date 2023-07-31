package com.medsoft.labmedial.dtos.response;

import java.util.Date;

public record ExercicioResponse(
        Long id,
        String nomeExercicio,
        Date dtaExercicio,
        String tipoExercicio,
        Integer qtdSemana,
        String descricao,
        NomePaciente paciente,
        Boolean situacao

) {
}
