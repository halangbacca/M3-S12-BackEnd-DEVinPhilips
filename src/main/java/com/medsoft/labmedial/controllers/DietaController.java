package com.medsoft.labmedial.controllers;

import com.medsoft.labmedial.dtos.request.DietaRequest;
import com.medsoft.labmedial.dtos.response.DietaResponse;
import com.medsoft.labmedial.services.DietaService;
import jakarta.validation.Valid;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/dietas", produces = "application/json")
@CrossOrigin
public class DietaController {

  private final DietaService service;

  @Autowired
  public DietaController(DietaService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<DietaResponse> cadastrarDieta(@Valid @RequestBody DietaRequest request) {
      DietaResponse dietaSalva = service.cadastrarDieta(request);
      return ResponseEntity.status(HttpStatus.CREATED)
              .body(dietaSalva);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DietaResponse> atualizarDieta(@Valid @RequestBody DietaRequest request,
                                                      @PathVariable Long id) {
    DietaResponse dietaEditada = service.atualizarDieta(request, id);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(dietaEditada);
  }

  @GetMapping
  public ResponseEntity<List<DietaResponse>> listarDietas(@RequestParam(required = false) String nomePaciente) {
    List<DietaResponse> dietaResponseList = service.listarDietasPorPaciente(nomePaciente);
    return ResponseEntity.status(HttpStatus.OK)
            .body(dietaResponseList);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Any> excluirDieta(@PathVariable Long id) {
    service.excluirDieta(id);
    return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(null);
  }
}