package com.medsoft.labmedial.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EMPRESA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "SLOGAN")
    private String slogan;

    @Column(name = "PALHETADECORES")
    private String palhetaDeCores;

    @Column(name = "LOGOTIPO")
    private String logotipo;

    @Column(name = "SITUACAO")
    private Boolean situacao;
}
