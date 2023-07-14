package com.medsoft.labmedial.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDate;

@Entity
@Table(name = "PACIENTE")
@Data
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TIPO_DE_USUARIO_ID")
    private Long tipoDeUsuarioId;
    @Column(name = "NOME_COMPLETO")
    private String nomeCompleto;
    private String genero;
    @Column(name = "DATA_DE_NASCIMENTO")
    private LocalDate dataDeNascimento;
    private String cpf;
    private String rg;
    @Column(name = "ESTADO_CIVIL")
    private String estadoCivil;
    private String telefone;
    private String email;
    private String naturalidade;
    @Column(name = "CONTATO_DE_EMERGENCIA")
    private String contatoDeEmergencia;
    @Column(name = "LISTA_DE_ALERGIAS")
    private String listaDeAlergias;
    @Column(name = "LISTA_DE_CUIDADOS_ESPECIFICOS")
    private String listaDeCuidadosEspecificos;
    private String convenio;
    @Column(name = "NUMERO_DO_CONVENIO")
    private String numeroDoConvenio;
    @Column(name = "VALIDADE_DO_CONVENIO")
    private LocalDate validadeDoConvenio;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "cep", referencedColumnName = "cep"),
            @JoinColumn(name = "cidade", referencedColumnName = "cidade"),
            @JoinColumn(name = "estado", referencedColumnName = "estado"),
            @JoinColumn(name = "logradouro", referencedColumnName = "logradouro"),
            @JoinColumn(name = "numero", referencedColumnName = "numero"),
            @JoinColumn(name = "complemento", referencedColumnName = "complemento"),
            @JoinColumn(name = "bairro", referencedColumnName = "bairro"),
            @JoinColumn(name = "pontoDeReferencia", referencedColumnName = "PONTO_DE_REFERENCIA"),
    })
    private Endereco endereco;
    @ReadOnlyProperty
    @Column(name = "STATUS_DO_SISTEMA")
    private Boolean statusDoSistema = true;
}
