package com.medsoft.labmedial.models;

import com.medsoft.labmedial.enums.TipoOcorrencia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "OCORRENCIA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TABLINK")
    private String tabLink;

    @Column(name = "CODLINK")
    private Long codLink;

    @Column(name = "REGATUAL")
    private String regAtual;

    @Column(name = "REGANTERIOR")
    private String regAnterior;

    @Column(name = "DTAOCORRENCIA")
    private Date dtaOcorrencia;

    @Column(name = "USUARIO")
    private String usuario;

    @Column(name = "TIPO")
    @Enumerated(EnumType.STRING)
    private TipoOcorrencia tipoOcorrencia;

}
