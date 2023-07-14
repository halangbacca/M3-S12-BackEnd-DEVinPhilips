package com.medsoft.labmedial.controllers;

import com.medsoft.labmedial.records.request.PacienteRequest;
import com.medsoft.labmedial.records.response.PacienteResponse;
import com.medsoft.labmedial.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<PacienteResponse> cadastrarPaciente(
            @Valid @RequestBody PacienteRequest request) {
        return service.cadastrarPaciente(request);
    }

    @GetMapping("/listar")
    public ResponseEntity<Object> listarPacientes() {
        return ResponseEntity.ok(service.listarPacientes());
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<PacienteResponse>> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> deletarPorId(@PathVariable Long id) {
        return service.deletarPorId(id);
    }
}