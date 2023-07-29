package com.medsoft.labmedial.controllers;

import com.medsoft.labmedial.dtos.request.PacienteRequest;
import com.medsoft.labmedial.dtos.response.PacienteResponse;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pacientes")
@CrossOrigin
public class PacienteController {
    @Autowired
    private PacienteService service;

    @PostMapping()
    public ResponseEntity<PacienteResponse> cadastrarPaciente( @Valid @RequestBody PacienteRequest request) {
        Paciente paciente = new Paciente(request);

        PacienteResponse novoPaciente = new PacienteResponse(service.cadastrarPaciente(paciente));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novoPaciente);
    }

    @GetMapping()
    public ResponseEntity<List<PacienteResponse>> listarPacientes() {

        List<PacienteResponse> pacienteResponse = service.listarPacientes()
                .stream()
                .map(PacienteResponse::new).toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(pacienteResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> buscarPorId(@PathVariable Long id) {

        PacienteResponse pacienteResponse = new PacienteResponse(service.buscarPorId(id));

        return ResponseEntity.status(HttpStatus.OK)
                .body(pacienteResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PacienteResponse> deletarPorId(@PathVariable Long id) {

        PacienteResponse pacienteResponse = new PacienteResponse(service.deletarPorId(id));

        return ResponseEntity.status(HttpStatus.OK)
                .body(pacienteResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> atualizarPaciente(@PathVariable Long id,
                                                              @Valid @RequestBody PacienteRequest request ){

        Paciente paciente = new Paciente(request);

        PacienteResponse novoPaciente = new PacienteResponse(service.atualizarPaciente(id, paciente));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novoPaciente);

    }
}