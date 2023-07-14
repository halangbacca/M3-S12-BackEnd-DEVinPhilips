package com.medsoft.labmedial.records.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.medsoft.labmedial.models.Endereco;

import java.time.LocalDate;

public record PacienteResponse(
        Long tipoDeUsuarioId,
        String nomeCompleto,
        String genero,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataDeNascimento,
        String cpf,
        String rg,
        String estadoCivil,
        String telefone,
        String email,
        String naturalidade,
        String contadoDeEmergencia,
        String listaDeAlergias,
        String listaDeCuidadosEspecificos,
        String convenio,
        String numeroDoConvenio,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate validadeDoConvenio,
        Endereco endereco
) {
}
