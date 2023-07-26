package com.medsoft.labmedial.controllers;

import com.medsoft.labmedial.dtos.request.MedicamentoRequest;
import com.medsoft.labmedial.dtos.response.MedicamentoResponse;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.mapper.MedicamentoMapper;
import com.medsoft.labmedial.models.Medicamento;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.services.MedicamentoService;
import com.medsoft.labmedial.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
@CrossOrigin
public class MedicamentoController {

    @Autowired
    private MedicamentoService service;

    @Autowired
    private PacienteService servicePaciente;

    @PostMapping()
    public ResponseEntity<MedicamentoResponse> cadastrarExame(@Valid @RequestBody MedicamentoRequest request) {

        Medicamento medicedicamento = service.cadastrarMedicamento(MedicamentoMapper.INSTANCE.requestToMedicamento(request));

        Paciente paciente = servicePaciente.buscarPorId(request.idPaciente());

        if(paciente != null){
            medicedicamento.setPaciente(paciente);
            Medicamento novoMedicamento = service.cadastrarMedicamento(medicedicamento);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(MedicamentoMapper.INSTANCE.medicamnetoToResponse(novoMedicamento));
        }

        throw new PacienteNotFoundExeception("Paciente não cadastrada!");
    }


    @GetMapping()
    public ResponseEntity<List<MedicamentoResponse>> listarMedicamento() {

        List<MedicamentoResponse> medicamentoResponses = service.listarMedicamentos()
                .stream()
                .map((Medicamento medicamento) -> MedicamentoMapper.INSTANCE.medicamnetoToResponse(medicamento)).toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(medicamentoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponse> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(MedicamentoMapper.INSTANCE.medicamnetoToResponse(service.buscarPorId(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarPorId(@PathVariable Long id) {

        if(service.deletarPorId(id)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponse> atualizarmedicamento(@PathVariable Long id,
                                                              @Valid @RequestBody MedicamentoRequest request ){


        Medicamento medicedicamento = service.cadastrarMedicamento(MedicamentoMapper.INSTANCE.requestToMedicamento(request));

        Paciente paciente = servicePaciente.buscarPorId(request.idPaciente());

        if(paciente != null){
            medicedicamento.setPaciente(paciente);
            Medicamento novoMedicamento = service.atualizarMedicamento(id, medicedicamento);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(MedicamentoMapper.INSTANCE.medicamnetoToResponse(novoMedicamento));
        }

        throw new PacienteNotFoundExeception("Paciente não cadastrado!");
    }

}
