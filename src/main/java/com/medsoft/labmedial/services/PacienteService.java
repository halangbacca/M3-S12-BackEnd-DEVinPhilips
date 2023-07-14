package com.medsoft.labmedial.services;

import com.medsoft.labmedial.mapper.PacienteMapper;
import com.medsoft.labmedial.models.Paciente;
import com.medsoft.labmedial.records.request.PacienteRequest;
import com.medsoft.labmedial.records.response.PacienteResponse;
import com.medsoft.labmedial.repositories.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository repository;

    public ResponseEntity<PacienteResponse> cadastrarPaciente(PacienteRequest request) {
        Paciente novoPaciente = PacienteMapper.INSTANCE.requestToPaciente(request);
        repository.save(novoPaciente);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PacienteMapper.INSTANCE.pacienteToResponse(novoPaciente));
    }

    public ResponseEntity<Object> listarPacientes() {
        return ResponseEntity.ok().body(
                repository.findAll().stream()
                        .map(PacienteMapper.INSTANCE::pacienteToResponse));
    }

    public ResponseEntity<Optional<PacienteResponse>> buscarPorId(Long id) {
        return ResponseEntity.ok().body(
                Optional.ofNullable(
                        repository.findById(id)
                                .map(PacienteMapper.INSTANCE::pacienteToResponse)
                                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado!"))
                )
        );
    }

    public ResponseEntity<Object> deletarPorId(Long id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado!"));
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
