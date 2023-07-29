package com.medsoft.labmedial.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
@Entity
@Table(name = "V_ESTATISTICA")
@Immutable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estatistica {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "GRUPO")
    private String grupo;

    @Column(name = "VALOR")
    private Long valor;



}
