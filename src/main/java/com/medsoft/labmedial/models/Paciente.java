package com.medsoft.labmedial.models;

import com.medsoft.labmedial.dtos.request.PacienteRequest;
import com.medsoft.labmedial.enums.EstadoCivil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "PACIENTE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "GENERO")
    private String genero;

    @Column(name = "DTANASCIMENTO")
    private LocalDate dtaNascimento;

    @Column(name = "CPF", updatable = false)
    private String cpf;

    @Column(name = "RG", updatable = false)
    private String rg;

    @Column(name = "ESTADOCIVIL")
    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;

    @Column(name = "TELEFONE")
    private String telefone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NATURALIDADE")
    private String naturalidade;

    @Column(name = "TELEMERGENCIA")
    private String telEmergencia;

    @Column(name = "CONVENIO")
    private String convenio;

    @Column(name = "NROCONVENIO")
    private String nroConvenio;

    @Column(name = "VALCONVENIO")
    private LocalDate validadeConvenio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "IDENDERECO")
    private Endereco endereco = new Endereco();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "IDPACIENTE")
    private Collection<Alergia> alergias = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "IDPACIENTE")
    private Collection<Precaucao> precaucoes = new ArrayList<>();;

    public Paciente(PacienteRequest pacienteRequest){

        this.nome = pacienteRequest.nome();
        this.genero = pacienteRequest.genero();
        this.dtaNascimento = pacienteRequest.dtaNascimento();
        this.cpf = pacienteRequest.cpf();
        this.rg = pacienteRequest.rg();
        this.estadoCivil = pacienteRequest.estadoCivil();
        this.telefone = pacienteRequest.telefone();
        this.email = pacienteRequest.email();
        this.naturalidade = pacienteRequest.naturalidade();
        this.telEmergencia = pacienteRequest.telEmergencia();
        this.convenio = pacienteRequest.convenio();
        this.nroConvenio = pacienteRequest.nroConvenio();
        this.validadeConvenio = pacienteRequest.validadeConvenio();

        this.endereco.setCep(pacienteRequest.cep());
        this.endereco.setCidade(pacienteRequest.cidade());
        this.endereco.setEstado(pacienteRequest.estado());
        this.endereco.setLogradouro(pacienteRequest.logradouro());
        this.endereco.setNumero(pacienteRequest.numero());
        this.endereco.setComplemento(pacienteRequest.complemento());
        this.endereco.setBairro(pacienteRequest.bairro());
        this.endereco.setReferencia(pacienteRequest.referencia());

        this.alergias.addAll(pacienteRequest.alergias());
        this.precaucoes.addAll(pacienteRequest.precaucoes());


    }

}
