package com.medsoft.labmedial.controllers;

import com.medsoft.labmedial.dtos.request.LoginRequest;
import com.medsoft.labmedial.dtos.request.UsuarioRequest;
import com.medsoft.labmedial.dtos.response.LoginResponse;
import com.medsoft.labmedial.dtos.response.UsuarioResponse;
import com.medsoft.labmedial.exceptions.UsuarioExeception;
import com.medsoft.labmedial.mapper.UsuarioMapper;
import com.medsoft.labmedial.models.Usuario;
import com.medsoft.labmedial.security.JWTUtil;
import com.medsoft.labmedial.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin
public class UsuarioController {


    private final UsuarioService service;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;

    public UsuarioController(UsuarioService service, PasswordEncoder passwordEncoder, JWTUtil jwtUtil, AuthenticationManager authManager) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    @PostMapping()
    public ResponseEntity<Object> cadastrarUsuario(@Valid @RequestBody UsuarioRequest request) {

        Optional<Usuario> optUsuario = this.service.buscarEmail(request.email());

        if(optUsuario.isPresent()){
            throw new UsuarioExeception("Email já cadastrado");
        }

        Usuario usuario = UsuarioMapper.INSTANCE.requestToUsuario(request);

        String encodedPass = passwordEncoder.encode(request.senha());
        usuario.setSenha(encodedPass);
        Usuario newUsuario = service.cadastrarUsuario(usuario);
        String token = jwtUtil.generateToken(newUsuario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("medsoft-token", token));

    }

    @GetMapping()
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarPorId(@PathVariable Long id) {

        if (service.deletarPorId(id)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@PathVariable Long id,
                                                            @Valid @RequestBody UsuarioRequest request) {

        Usuario novoUsuario = service.atualizarUsuario(id, UsuarioMapper.INSTANCE.requestToUsuario(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UsuarioMapper.INSTANCE.usuarioToResponse(novoUsuario));

    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUsuario(@Valid @RequestBody LoginRequest usuarioLogin){
        try {

            Optional<Usuario> opUsuario = service.buscarEmail(usuarioLogin.email());

            if(opUsuario.isEmpty()){
                throw new UsuarioExeception("Credenciais Inválidas");
            }

            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(usuarioLogin.email(), usuarioLogin.senha());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(opUsuario.get());

            LoginResponse loginResponse = new LoginResponse(opUsuario.get(),token);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(loginResponse);

        }catch (AuthenticationException authExc){

            throw new UsuarioExeception("Credenciais Inválidas");
        }
    }

}