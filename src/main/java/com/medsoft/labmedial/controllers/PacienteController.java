package com.medsoft.labmedial.controllers;

import com.medsoft.labmedial.dtos.request.PacienteRequest;
import com.medsoft.labmedial.dtos.response.PacienteResponse;
import com.medsoft.labmedial.mapper.PacienteMapper;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.services.PacienteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<PacienteResponse> cadastrarPaciente( @Valid @RequestBody PacienteRequest request) {
        Paciente paciente = new Paciente(request);

        PacienteResponse novoPaciente = new PacienteResponse(service.cadastrarPaciente(paciente));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novoPaciente);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PacienteResponse>> listarPacientes() {

        List<PacienteResponse> pacienteResponse = service.listarPacientes()
                .stream()
                .map(PacienteMapper.INSTANCE::pacienteToResponse).toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(pacienteResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> buscarPorId(@PathVariable Long id) {

        PacienteResponse pacienteResponse = new PacienteResponse(service.buscarPorId(id));

        return ResponseEntity.status(HttpStatus.OK)
                .body(pacienteResponse);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> deletarPorId(@PathVariable Long id) {

        if(service.deletarPorId(id)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        return null;
    }
}