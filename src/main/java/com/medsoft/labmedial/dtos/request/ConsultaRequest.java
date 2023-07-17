package com.medsoft.labmedial.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record ConsultaRequest(

        @NotBlank(message = "O preenchimento da motivo da consulta é obrigatório!")
        @Size(min = 8, max = 64)
        String motivo,

        Date dtaConsulta,

        @NotBlank(message = "O problema da consulta é obrigatório!")
        @Size(min = 16, max = 1024)
        String problema,

        String medicacao,

        @NotBlank(message = "O precaução da consulta é  obrigatório!")
        @Size(min = 16, max = 256)
        String precausao,

        Long idPaciente

) {
}
