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

        Medicamento medicamento = service.cadastrarMedicamento(MedicamentoMapper.INSTANCE.requestToMedicamento(request));

        Paciente paciente = servicePaciente.buscarPorId(request.idPaciente());

        if (paciente != null) {
            medicamento.setPaciente(paciente);
            medicamento.setSituacao(true);
            Medicamento novoMedicamento = service.cadastrarMedicamento(medicamento);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(MedicamentoMapper.INSTANCE.medicamentoToMedicamentoResponse(novoMedicamento));
        }

        throw new PacienteNotFoundExeception("Paciente não cadastrado!");
    }


    @GetMapping()
    public ResponseEntity<List<MedicamentoResponse>> listarMedicamento(@RequestParam(required = false) String nomePaciente) {
        List<MedicamentoResponse> medicamentoResponses = service.listarMedicamentosPorPaciente(nomePaciente);
        return ResponseEntity.status(HttpStatus.OK)
                .body(medicamentoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponse> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(MedicamentoMapper.INSTANCE.medicamentoToMedicamentoResponse(service.buscarPorId(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarPorId(@PathVariable Long id) {

        if (service.deletarPorId(id)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponse> atualizarMedicamento(@PathVariable Long id,
                                                                    @Valid @RequestBody MedicamentoRequest request) {

        Medicamento medicamentoEditado = service.buscarPorId(id);

        Paciente paciente = servicePaciente.buscarPorId(request.idPaciente());

        if (paciente != null) {
            medicamentoEditado.setPaciente(paciente);
            medicamentoEditado.setSituacao(true);
            service.atualizarMedicamento(id, MedicamentoMapper.INSTANCE.requestToMedicamento(request));

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(MedicamentoMapper.INSTANCE.medicamentoToMedicamentoResponse(medicamentoEditado));
        }

        throw new PacienteNotFoundExeception("Paciente não cadastrado!");
    }
}
