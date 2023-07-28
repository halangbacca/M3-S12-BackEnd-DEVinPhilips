package com.medsoft.labmedial.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.medsoft.labmedial.enums.TipoExercicio;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "EXERCICIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOMEEXERCICIO")
    private String nomeExercicio;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "DTAEXERCICIO")
    private Date dtaExercicio;

    @Column(name = "TIPOEXERCICIO")
    @Enumerated(EnumType.STRING)
    private TipoExercicio tipoExercicio;

    @Column(name = "QTDSEMANA")
    private Integer qtdSemana;

    @Column(name = "DESCRICAO")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "IDPACIENTE")
    private Paciente paciente;

    @Column(name = "SITUACAO")
    private Boolean situacao;

}
