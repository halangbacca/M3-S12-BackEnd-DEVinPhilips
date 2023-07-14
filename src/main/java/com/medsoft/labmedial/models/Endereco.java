package com.medsoft.labmedial.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ENDERECO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String cidade;
    private String estado;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    @Column(name = "PONTO_DE_REFERENCIA")
    private String pontoDeReferencia;
}