package com.medsoft.labmedial.controllers;

import com.medsoft.labmedial.dtos.request.ExameRequest;
import com.medsoft.labmedial.dtos.response.ExameResponse;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.mapper.ExameMapper;
import com.medsoft.labmedial.models.Exame;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.services.ExameService;
import com.medsoft.labmedial.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/exames")
public class ExameController {


    private final ExameService service;

    private final PacienteService servicePaciente;

    public ExameController(ExameService service, PacienteService servicePaciente) {
        this.service = service;
        this.servicePaciente = servicePaciente;
    }

    @PostMapping()
    public ResponseEntity<ExameResponse> cadastrarExame(@Valid @RequestBody ExameRequest request) {

        Exame exame = ExameMapper.INSTANCE.requestToExame(request);
        Paciente paciente = servicePaciente.buscarPorId(request.idPaciente());

        if (paciente != null) {
            exame.setPaciente(paciente);
            Exame newExame = service.cadastrarExame(exame);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ExameMapper.INSTANCE.exameToResponse(newExame));
        }

        throw new PacienteNotFoundExeception("Paciente não cadastrado!");

    }

    @GetMapping()
    public ResponseEntity<List<ExameResponse>> listarExames(@RequestParam(required = false) String nomePaciente) {
        String decodedName = null;
        if (nomePaciente != null) {
            decodedName = URLDecoder.decode(nomePaciente, StandardCharsets.UTF_8);
        }
        List<ExameResponse> consultaResponses = service.listarExames(decodedName)
                .stream().map(ExameMapper.INSTANCE::exameToResponse).toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(consultaResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExameResponse> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(ExameMapper.INSTANCE.exameToResponse(service.buscarPorId(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarPorId(@PathVariable Long id) {

        if (service.deletarPorId(id)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExameResponse> atualizarExame(@PathVariable Long id,
                                                        @Valid @RequestBody ExameRequest request) {

        Exame exame = ExameMapper.INSTANCE.requestToExame(request);
        Paciente paciente = servicePaciente.buscarPorId(request.idPaciente());

        if (paciente != null) {
            exame.setPaciente(paciente);
            Exame newExame = service.atualizarExame(id, exame);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ExameMapper.INSTANCE.exameToResponse(newExame));
        }

        throw new PacienteNotFoundExeception("Paciente não cadastrado!");

    }

}
