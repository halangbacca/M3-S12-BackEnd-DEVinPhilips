package com.medsoft.labmedial.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.medsoft.labmedial.enums.TipoDieta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

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

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @Column(name = "DTADIETA")
  private Date dtaDieta;

  @Column(name = "TIPODIETA")
  @Enumerated(EnumType.STRING)
  private TipoDieta tipoDieta;

  @Column(name = "DESCRICAO")
  private String descricao;

  @ManyToOne
  @JoinColumn(name = "IDPACIENTE")
  @ToString.Exclude
  private Paciente paciente;

  @Column(name = "SITUACAO")
  private Boolean situacao;

}
