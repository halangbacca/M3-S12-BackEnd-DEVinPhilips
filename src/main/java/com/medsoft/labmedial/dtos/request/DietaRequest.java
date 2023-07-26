package com.medsoft.labmedial.dtos.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.medsoft.labmedial.enums.TipoDieta;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DietaRequest(

        @NotNull(message = "O nome da dieta é obrigatório")
        String nomeDieta,

        @NotNull(message = "A data da dieta é obrigatória")
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate dtaDieta,

        TipoDieta tipoDieta,
        String descricao,

        @NotNull(message = "O id do paciente é obrigatório")
        Long idPaciente,
        Boolean situacao
) {
}
