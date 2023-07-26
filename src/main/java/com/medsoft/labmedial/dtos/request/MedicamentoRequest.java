package com.medsoft.labmedial.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record MedicamentoRequest(

        @NotBlank(message = "O preenchimento da descrição do medicamento é obrigatório!")
        @Size(min = 5, max = 100)
        String descricao,

        @NotNull(message = "A data do medicamento é obrigatório")
        @JsonFormat(pattern = "dd-MM-yyyy")
        Date dtaMedicamento,

        @NotBlank(message = "O tipo do medicamento é obrigatório!")
        String tipo,

        @NotBlank(message = "O unidade do medicamento é obrigatório!")
        String unidade,

        @NotNull(message = "O quantidade do medicamento é obrigatório!")
        Long quantidade,

        @NotBlank(message = "O observação do medicamento é  obrigatório!")
        @Size(min = 10, max = 1000)
        String observacao,

        Long idPaciente

) {
}
