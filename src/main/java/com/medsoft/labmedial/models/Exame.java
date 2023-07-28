package com.medsoft.labmedial.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "EXAME")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRICAO")
    private String descricao;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "DTAEXAME")
    private Date dtaExame;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "DOCUMENTO")
    private String documento;

    @Column(name = "RESULTADO")
    private String resultado;

    @ManyToOne()
    @JoinColumn(name = "IDPACIENTE")
    private Paciente paciente;

    @Column(name = "SITUACAO")
    private Boolean situacao;

}
