package com.medsoft.labmedial.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record ExameRequest(

        @NotBlank(message = "O preenchimento da descrição do exame é obrigatório!")
        @Size(min = 8, max = 64)
        String descricao,

        //@NotEmpty(message = "A data do exame é obrigatório!")
        Date dtaExame,

        @NotBlank(message = "O tipo do exame é obrigatório!")
        @Size(min = 4, max = 32)
        String tipo,

        String documento,

        @NotBlank(message = "O resultado do exame  obrigatório!")
        @Size(min = 16, max = 1024)
        String resultado,

        Long idPaciente

) {
}
