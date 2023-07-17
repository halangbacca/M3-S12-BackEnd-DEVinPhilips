package com.medsoft.labmedial.controllers;

import com.medsoft.labmedial.dtos.request.ConsultaRequest;
import com.medsoft.labmedial.dtos.request.MedicamentoRequest;
import com.medsoft.labmedial.dtos.response.ConsultaResponse;
import com.medsoft.labmedial.dtos.response.MedicamentoResponse;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.mapper.ConsultaMapper;
import com.medsoft.labmedial.mapper.MedicamentoMapper;
import com.medsoft.labmedial.models.Consulta;
import com.medsoft.labmedial.models.Medicamento;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.services.MedicamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<MedicamentoResponse> cadastrarExame(@Valid @RequestBody MedicamentoRequest request) {

        Medicamento novoMedicamento = service.cadastrarMedicamento(MedicamentoMapper.INSTANCE.requestToMedicamento(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                    .body(MedicamentoMapper.INSTANCE.medicamnetoToResponse(novoMedicamento));
        }


    @GetMapping("/listar")
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

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> deletarPorId(@PathVariable Long id) {

        if(service.deletarPorId(id)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        return null;
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<MedicamentoResponse> atualizarmedicamento(@PathVariable Long id,
                                                              @Valid @RequestBody MedicamentoRequest request ){

        Medicamento medicamento = MedicamentoMapper.INSTANCE.requestToMedicamento(request);
        Medicamento novoMedicamento = service.atualizarMedicamento(id,medicamento);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MedicamentoMapper.INSTANCE.medicamnetoToResponse(novoMedicamento));

    }

}
