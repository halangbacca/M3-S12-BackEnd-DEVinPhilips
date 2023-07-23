package com.medsoft.labmedial.dtos.response;

import java.util.Date;

public record ExercicioResponse(
        String nomeExercicio,
        Date dtaExercicio,
        String tipoExercicio,
        Integer qtdSemana,
        String descricao,
        Boolean situacao

) {
}
