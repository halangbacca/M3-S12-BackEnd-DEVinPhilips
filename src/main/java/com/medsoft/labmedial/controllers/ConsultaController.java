package com.medsoft.labmedial.controllers;

import com.medsoft.labmedial.dtos.request.ConsultaRequest;
import com.medsoft.labmedial.dtos.response.ConsultaResponse;
import com.medsoft.labmedial.exceptions.ConsultaNotFoundExeception;
import com.medsoft.labmedial.mapper.ConsultaMapper;
import com.medsoft.labmedial.models.Consulta;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.services.ConsultaService;
import com.medsoft.labmedial.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService service;
    @Autowired
    private PacienteService servicePaciente;

    @PostMapping("/cadastrar")
    public ResponseEntity<ConsultaResponse> cadastrarExame(@Valid @RequestBody ConsultaRequest request) {

        Consulta consulta = ConsultaMapper.INSTANCE.requestToConsulta(request);
        Paciente paciente = servicePaciente.buscarPorId(request.idPaciente());

        if(paciente != null){
            consulta.setPaciente(paciente);
            Consulta newConsulta = service.cadastrarConsulta(consulta);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ConsultaMapper.INSTANCE.consultaToResponse(newConsulta));
        }

        throw new ConsultaNotFoundExeception("Consulta não cadastrada!");

    }

    @GetMapping("/listar")
    public ResponseEntity<List<ConsultaResponse>> listarConsulta() {

        List<ConsultaResponse> consultaResponses = service.listarConsultas()
                .stream()
                .map((Consulta consulta) -> ConsultaMapper.INSTANCE.consultaToResponse(consulta)).toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(consultaResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponse> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(ConsultaMapper.INSTANCE.consultaToResponse(service.buscarPorId(id)));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> deletarPorId(@PathVariable Long id) {

        if(service.deletarPorId(id)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        return null;
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ConsultaResponse> atualizarConsulta(@PathVariable Long id,
                                                              @Valid @RequestBody ConsultaRequest request ){

        Consulta consulta = ConsultaMapper.INSTANCE.requestToConsulta(request);
        Paciente paciente = servicePaciente.buscarPorId(request.idPaciente());

        if(paciente != null){
            consulta.setPaciente(paciente);
            Consulta newConsulta = service.atualizarConsulta(id, consulta);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ConsultaMapper.INSTANCE.consultaToResponse(newConsulta));
        }

        throw new ConsultaNotFoundExeception("Consulta não cadastrado");

    }

}
