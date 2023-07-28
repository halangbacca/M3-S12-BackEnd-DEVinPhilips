package com.medsoft.labmedial.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.medsoft.labmedial.enums.TipoExercicio;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record ExercicioRequest(
        @NotNull(message = "O nome da série de exercícios é obrigatório")
        @Size(min = 5, max = 100, message = "O nome o exercício deve ter entre 5 e 100 caracteres")
        String nomeExercicio,

        @NotNull(message = "A data do exercício é obrigatória")
        @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
        Date dtaExercicio,

        TipoExercicio tipoExercicio,

        @NotNull(message = "A quantidade de exercício por semana é obrigatório")
        Integer qtdSemana,

        @NotNull(message = "A descrição do exercício é obrigatória")
        @Size(min = 10, max = 1000, message = "A descrição deve ter entre 10 e 1000 caracteres")
        String descricao,

        @NotNull(message = "O id do paciente é obrigatório")
        Long idPaciente
) {
}
