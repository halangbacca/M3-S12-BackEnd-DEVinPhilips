package com.medsoft.labmedial.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "CONSULTA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MOTIVO")
    private String motivo;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "DTACONSULTA")
    private Date dtaConsulta;

    @Column(name = "PROBLEMA")
    private String problema;

    @Column(name = "MEDICACAO")
    private String medicacao;

    @Column(name = "PRECAUCAO")
    private String precaucao;

    @ManyToOne()
    @JoinColumn(name = "IDPACIENTE")
    private Paciente paciente;

    @Column(name = "SITUACAO")
    private Boolean situacao;

}
