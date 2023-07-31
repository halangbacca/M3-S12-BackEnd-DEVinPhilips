package com.medsoft.labmedial.controllers;

import com.medsoft.labmedial.dtos.request.EmpresaRequest;
import com.medsoft.labmedial.dtos.response.EmpresaResponse;
import com.medsoft.labmedial.mapper.EmpresaMapper;
import com.medsoft.labmedial.models.Empresa;
import com.medsoft.labmedial.services.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {
    @Autowired
    private EmpresaService service;

    @PostMapping()
    public ResponseEntity<EmpresaResponse> cadastrarEmpresa(@Valid @RequestBody EmpresaRequest request) {
        Empresa empresa = EmpresaMapper.INSTANCE.requestToEmpresa(request);

        Empresa novaEmpresa = service.cadastrarEmpresa(empresa);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EmpresaMapper.INSTANCE.empresaToResponse(novaEmpresa));
    }

    @GetMapping()
    public ResponseEntity<List<EmpresaResponse>> listarEmpresas() {

        List<EmpresaResponse> empresasResponses = service.listarEmpresas()
                .stream()
                .map(EmpresaMapper.INSTANCE::empresaToResponse).toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(empresasResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponse> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(EmpresaMapper.INSTANCE.empresaToResponse(service.buscarPorId(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarPorId(@PathVariable Long id) {

        if (service.deletarPorId(id)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponse> atualizarEmpresa(@PathVariable Long id,
                                                            @Valid @RequestBody EmpresaRequest request) {

        Empresa novaEmpresa = service.atualizarEmpresa(id, EmpresaMapper.INSTANCE.requestToEmpresa(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EmpresaMapper.INSTANCE.empresaToResponse(novaEmpresa));
    }
}
