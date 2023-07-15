package com.medsoft.labmedial.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.medsoft.labmedial.enums.EstadoCivil;
import com.medsoft.labmedial.models.Alergia;
import com.medsoft.labmedial.models.Endereco;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.models.Precaucao;

import java.time.LocalDate;
import java.util.Collection;

public record PacienteResponse(

        Long id,

        String nome,

        String genero,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dtaNascimento,

        String cpf,

        String rg,

        EstadoCivil estadoCivil,

        String telefone,

        String email,

        String naturalidade,

        String telEmergencia,

        String convenio,

        String nroConvenio,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate validadeConvenio,

        //Endereço
        String cep,

        String cidade,

        String estado,

        String logradouro,

        String numero,

        String complemento,

        String bairro,

        String referencia,

        Collection<Alergia> alergias,

        Collection<Precaucao> precaucoes
) {

        public PacienteResponse(Paciente paciente){

                this(paciente.getId(), paciente.getNome(), paciente.getGenero(), paciente.getDtaNascimento(),
                        paciente.getCpf(),paciente.getRg(),paciente.getEstadoCivil(),paciente.getTelefone(),paciente.getEmail(),
                        paciente.getNaturalidade(),paciente.getTelEmergencia(),paciente.getConvenio(),paciente.getNroConvenio(),
                        paciente.getValidadeConvenio(),validaEndereco(paciente).getCep(),validaEndereco(paciente).getCidade(),
                        validaEndereco(paciente).getEstado(),validaEndereco(paciente).getLogradouro(),validaEndereco(paciente).getNumero(),
                        validaEndereco(paciente).getComplemento(),validaEndereco(paciente).getBairro(),validaEndereco(paciente).getReferencia(),
                        paciente.getAlergias(), paciente.getPrecaucoes());

        }

        private static  Endereco validaEndereco(Paciente paciente){
            Endereco endereco = new Endereco();

            if(paciente.getEndereco()!=null){
                return paciente.getEndereco();
            }

            return endereco;
        }

}
