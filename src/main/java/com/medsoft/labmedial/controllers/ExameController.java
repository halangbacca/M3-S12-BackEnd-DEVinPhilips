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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exames")
public class ExameController {

    @Autowired
    private ExameService service;
    @Autowired
    private PacienteService servicePaciente;

    @PostMapping()
    public ResponseEntity<ExameResponse> cadastrarExame(@Valid @RequestBody ExameRequest request) {

        Exame exame = ExameMapper.INSTANCE.requestToExame(request);
        Paciente paciente = servicePaciente.buscarPorId(request.idPaciente());

        if(paciente != null){
            exame.setPaciente(paciente);
            Exame newExame = service.cadastrarExame(exame);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ExameMapper.INSTANCE.exameToResponse(newExame));
        }

        throw new PacienteNotFoundExeception("Paciente não cadastrado");

    }

    @GetMapping()
    public ResponseEntity<List<ExameResponse>> listarPacientes() {

        List<ExameResponse> exameResponses = service.listarExames()
                .stream()
                .map((Exame exame) -> ExameMapper.INSTANCE.exameToResponse(exame)).toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(exameResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExameResponse> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(ExameMapper.INSTANCE.exameToResponse(service.buscarPorId(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarPorId(@PathVariable Long id) {

        if(service.deletarPorId(id)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExameResponse> atualizarPaciente(@PathVariable Long id,
                                                              @Valid @RequestBody ExameRequest request ){

        Exame exame = ExameMapper.INSTANCE.requestToExame(request);
        Paciente paciente = servicePaciente.buscarPorId(request.idPaciente());

        if(paciente != null){
            exame.setPaciente(paciente);
            Exame newExame = service.atualizarPaciente(id, exame);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ExameMapper.INSTANCE.exameToResponse(newExame));
        }

        throw new PacienteNotFoundExeception("Paciente não cadastrado");

    }

}
