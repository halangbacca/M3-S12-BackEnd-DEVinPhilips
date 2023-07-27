package com.medsoft.labmedial.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "MEDICAMENTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO")
    private String descricao;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "DTAMEDICAMENTO")
    private Date dtaMedicamento;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "UNIDADE")
    private String unidade;

    @Column(name = "QUANTIDADE")
    private Long quantidade;

    @Column(name = "OBSERVACAO")
    private String observacao;

    @ManyToOne()
    @JoinColumn(name = "IDPACIENTE")
    private Paciente paciente;

    @Column(name = "SITUACAO")
    private Boolean situacao;

}
