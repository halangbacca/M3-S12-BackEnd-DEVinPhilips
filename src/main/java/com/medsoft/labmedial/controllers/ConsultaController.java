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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
@CrossOrigin
public class ConsultaController {

    @Autowired
    private ConsultaService service;
    @Autowired
    private PacienteService servicePaciente;

    @PostMapping()
    public ResponseEntity<ConsultaResponse> cadastrarExame(@Valid @RequestBody ConsultaRequest request,
                                                           @RequestHeader(value = "Authorization") String authorization) {

        Consulta consulta = ConsultaMapper.INSTANCE.requestToConsulta(request);
        Paciente paciente = servicePaciente.buscarPorId(request.idPaciente());

        if (paciente != null) {
            consulta.setPaciente(paciente);
            Consulta newConsulta = service.cadastrarConsulta(consulta, authorization);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ConsultaMapper.INSTANCE.consultaToResponse(newConsulta));
        }

        throw new ConsultaNotFoundExeception("Consulta não cadastrada!");

    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponse>> listarConsultas(@RequestParam(required = false) String nomePaciente) {
        String decodedName = null;
        if (nomePaciente != null) {
            decodedName = URLDecoder.decode(nomePaciente, StandardCharsets.UTF_8);
        }
        List<ConsultaResponse> consultaResponses = service.listarConsultas(decodedName)
                .stream().map(ConsultaMapper.INSTANCE::consultaToResponse).toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(consultaResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponse> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(ConsultaMapper.INSTANCE.consultaToResponse(service.buscarPorId(id)));
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<ConsultaResponse>> buscarPorPacienteId(@PathVariable Long id) {
        List<ConsultaResponse> consultaResponse = service.listarConsultasPorPacienteId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(consultaResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarPorId(@PathVariable Long id,
                                               @RequestHeader(value = "Authorization") String authorization) {

        if (service.deletarPorId(id, authorization)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponse> atualizarConsulta(@PathVariable Long id,
                                                              @Valid @RequestBody ConsultaRequest request,
                                                              @RequestHeader(value = "Authorization") String authorization) {

        Consulta consulta = ConsultaMapper.INSTANCE.requestToConsulta(request);
        Paciente paciente = servicePaciente.buscarPorId(request.idPaciente());

        if (paciente != null) {
            consulta.setPaciente(paciente);
            Consulta newConsulta = service.atualizarConsulta(id, consulta, authorization);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ConsultaMapper.INSTANCE.consultaToResponse(newConsulta));
        }

        throw new ConsultaNotFoundExeception("Consulta não cadastrada!");

    }

}
