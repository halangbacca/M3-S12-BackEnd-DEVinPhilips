package com.medsoft.labmedial.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medsoft.labmedial.enums.TipoDieta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "DIETA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Dieta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column(name = "NOMEDIETA")
  private String nomeDieta;

  @Column(name = "DTADIETA")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDate dtaDieta;

  @Column(name = "TIPODIETA")
  @Enumerated(EnumType.STRING)
  private TipoDieta tipoDieta;

  @Column(name = "DESCRICAO")
  private String descricao;

  @ManyToOne
  @JoinColumn(name = "IDPACIENTE")
  private Paciente paciente;

  @Column(name = "SITUACAO")
  private Boolean situacao;

}
