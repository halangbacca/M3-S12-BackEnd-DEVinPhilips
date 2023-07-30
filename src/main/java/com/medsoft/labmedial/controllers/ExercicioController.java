package com.medsoft.labmedial.controllers;

import com.medsoft.labmedial.dtos.request.ExercicioRequest;
import com.medsoft.labmedial.dtos.response.ExercicioResponse;
import com.medsoft.labmedial.services.ExercicioService;
import jakarta.validation.Valid;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/exercicios", produces = "application/json")
@CrossOrigin
public class ExercicioController {
  private final ExercicioService service;

  @Autowired
  public ExercicioController(ExercicioService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<ExercicioResponse> cadastrarExercicio(@Valid
                                                              @RequestBody ExercicioRequest request,
                                                              @RequestHeader(value = "Authorization") String authorization) {
    ExercicioResponse exercicioSalvo = service.cadastrarExercicio(request, authorization);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(exercicioSalvo);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ExercicioResponse> atualizarExercicio(@Valid @RequestBody ExercicioRequest request,
                                                      @PathVariable Long id,
                                                      @RequestHeader(value = "Authorization") String authorization) {
    ExercicioResponse exercicioEditado = service.atualizarExercicio(request, id, authorization);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(exercicioEditado);
  }

  @GetMapping
  public ResponseEntity<List<ExercicioResponse>> listarExercicios(@RequestParam(required = false) String nomePaciente) {
    List<ExercicioResponse> exercicioResponseList = service.listarExerciciosPorPaciente(nomePaciente);
    return ResponseEntity.status(HttpStatus.OK)
            .body(exercicioResponseList);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Any> excluirExercicio(@PathVariable Long id,
                                              @RequestHeader(value = "Authorization") String authorization) {
    service.excluirExercicio(id,authorization);
    return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(null);
  }
}
