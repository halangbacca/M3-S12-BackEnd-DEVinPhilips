package com.medsoft.labmedial.controllers;

import com.medsoft.labmedial.models.Ocorrencia;
import com.medsoft.labmedial.services.OcorrenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin
public class OcorrenciaController {

    private final OcorrenciaService ocorrenciaService;

    public OcorrenciaController(OcorrenciaService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }

    @GetMapping
    public ResponseEntity<List<Ocorrencia>> buscarOcorrencia(@RequestParam Long codLink, @RequestParam String tabLink){

        return ResponseEntity.status(HttpStatus.OK).body(this.ocorrenciaService.buscarOcorrencia(codLink,tabLink));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Ocorrencia>> listarOcorrencia(){

        return ResponseEntity.status(HttpStatus.OK).body(this.ocorrenciaService.listarOcorrencia());
    }


}
