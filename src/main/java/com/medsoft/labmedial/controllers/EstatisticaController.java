package com.medsoft.labmedial.controllers;

import com.medsoft.labmedial.models.Estatistica;
import com.medsoft.labmedial.services.EstatisticaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estatistica")
public class EstatisticaController {

    private final EstatisticaService service;

    public EstatisticaController(EstatisticaService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Estatistica>> listarPacientes() {

        return ResponseEntity.status(HttpStatus.OK)
                .body(service.listarEstatistica());
    }
}
