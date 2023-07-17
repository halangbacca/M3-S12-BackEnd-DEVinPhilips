package com.medsoft.labmedial.controllers;

import com.medsoft.labmedial.dtos.request.PacienteRequest;
import com.medsoft.labmedial.dtos.request.UsuarioRequest;
import com.medsoft.labmedial.dtos.response.ConsultaResponse;
import com.medsoft.labmedial.dtos.response.PacienteResponse;
import com.medsoft.labmedial.dtos.response.UsuarioResponse;
import com.medsoft.labmedial.exceptions.PacienteNotFoundExeception;
import com.medsoft.labmedial.mapper.ConsultaMapper;
import com.medsoft.labmedial.mapper.UsuarioMapper;
import com.medsoft.labmedial.models.Consulta;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.models.Usuario;
import com.medsoft.labmedial.services.PacienteService;
import com.medsoft.labmedial.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponse> cadastrarUsuario(@Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = UsuarioMapper.INSTANCE.requestToUsuario(request);


            Usuario novoUsuario = service.cadastrarUsuario(usuario);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UsuarioMapper.INSTANCE.usuarioToResponse(novoUsuario));

    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {

        List<UsuarioResponse> usuarioResponses = service.listarUsuarios()
                .stream()
                .map((Usuario usuario) -> UsuarioMapper.INSTANCE.usuarioToResponse(usuario)).toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(usuarioResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(UsuarioMapper.INSTANCE.usuarioToResponse(service.buscarPorId(id)));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> deletarPorId(@PathVariable Long id) {

        if(service.deletarPorId(id)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        return null;
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@PathVariable Long id,
                                                              @Valid @RequestBody UsuarioRequest request ){

        Usuario novoUsuario = service.atualizarUsuario(id, UsuarioMapper.INSTANCE.requestToUsuario(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UsuarioMapper.INSTANCE.usuarioToResponse(novoUsuario));

    }
}