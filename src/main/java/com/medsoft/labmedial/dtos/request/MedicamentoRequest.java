package com.medsoft.labmedial.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record MedicamentoRequest(

        @NotBlank(message = "O preenchimento da descrição do medicamento é obrigatório!")
        @Size(min = 8, max = 64)
        String descricao,

        //@NotEmpty(message = "A data do exame é obrigatório!")
        @JsonFormat(pattern = "dd/MM/yyyy")
        Date dtaMedicamento,

        @NotBlank(message = "O tipo do medicamento é obrigatório!")
        @Size(min = 4, max = 32)
        String tipo,

        String unidade,

        @NotBlank(message = "O resultado do exame  obrigatório!")
        @Size(min = 16, max = 1024)
        String observacao

) {
}
